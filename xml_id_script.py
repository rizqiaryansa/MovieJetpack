import os
import shutil
import os.path
import git
import optparse
from optparse import OptionParser
from git import Repo
import time

COMMITS_TO_PRINT = 5
patternLayout = "res/layout/"
patternIdView = "android:id=\"@+id/"
patternBeforeFile = "---"
patternAfterFile = "+++"
patternBeforeIdView = "-"
patternAfterIdView = "+"
patternRemoveIdView = "-"
patternTagOpenIdView = "<"
patternTagCloseFullIdView = "</"
patternTagCloseIdView = "/>"
patternAddIdView = "+"
patternRemoveFile = "/dev/null"
beforePath = "before/"
afterPath = "after/"
pathBefore = "before"
pathAfter = "after"

patternGitDiff = "diff --git"

def print_repository(repo):
    print('Repo description: {}'.format(repo.description))
    print('Repo active branch is {}'.format(repo.active_branch))
    for remote in repo.remotes:
        print('Remote named "{}" with URL "{}"'.format(remote, remote.url))
    print('Last commit for repo is {}.'.format(str(repo.head.commit.hexsha)))


def print_commit(commit):
    print('----')
    print(str(commit.hexsha))
    print("\"{}\" by {} ({})".format(commit.summary,
                                     commit.author.name,
                                     commit.author.email))
    print(str(commit.authored_datetime))
    print(str("count: {} and size: {}".format(commit.count(),
                                              commit.size)))


def writeFile(input):
    # fileName = "/temp/input.txt"

    tempName = "dump_input.txt"
    testCase = "test_case.txt"

    if not os.path.exists(tempName):
        createDumpFile(tempName, input)
    elif os.path.exists(tempName):
        removedPackFile(tempName, pathBefore, pathAfter)
        createDumpFile(tempName, input)

    nameFileAfter = ""
    nameFileBefore = ""
    nameFileCompare = ""

    setIdAfter = set()
    setIdBefore = set()
    setRemovedFile = set()
    
    setRemovedId = set()
    setAddId = set()

    setFileBefore = set()
    setFileAfter = set()

    modul = ""

    newId = ""

    tempModul = list()
    tempNameFile = set()
    tempOnlyModul = set()

    strLineRemove = ""
    isLineRemove = False
    nameFileRemoved = ""

    starTime = None
    endTime = None

    afterNewId = ""
    beforeNewId = ""

    nameView = ""

    isChangedId = bool
    isRemovedId = bool
    
    dictBeforeChangedId = dict()
    dictAfterChangedId = dict()

    dictAddId = dict()
    dictChangedId = dict()
    dictDeletedId = dict()

    count = 0

    if(os.path.exists(testCase)):
        with open(testCase, "r") as fCase:
            strStrip = fCase.read().splitlines()
            tempCase = set(strStrip)
            print("tempCase: " + str(tempCase))
        
        z = open(testCase, "r")

        for lineModul in z:
            strOnlyModul = lineModul.replace("\n", "").split("/")[0]
            stryOnlyName = lineModul.replace("\n", "").split("/")
            tempModul.append(strOnlyModul)
            if(len(stryOnlyName) > 1):
                modulSlash = strOnlyModul + "/" + stryOnlyName[1]
                tempNameFile.add(str(modulSlash.strip()))
            
            strOnlyModuleName = lineModul.replace("\n", "").split(" ")
            if(len(strOnlyModuleName) == 1 and len(stryOnlyName) == 1):
                tempOnlyModul.add(str(strOnlyModuleName[0]))
            

        print("tempModule: " + str(tempModul))
        print("tempNameFile: " + str(tempNameFile))
        print("tempOnlyModule: " + str(tempOnlyModul))

    starTime = time.time()

    f = open(tempName, "r")
    for line in f:
        if(patternBeforeFile in line[:3]):
            if patternLayout in line:
                modul = str(line[6:].split("/")[0])
                nameFileBefore = getFile(patternLayout, patternBeforeFile, line, pathBefore)
                nameFileCompare = modul + "/" + getNameFileXML(nameFileBefore)
                isLineRemove = False
                nameFileRemoved = modul + "/" + getNameFileXML(nameFileBefore)
                # print(nameFileCompare)
        
        if(patternAfterFile in line[:3]):
            # print(line[:3])
            
            strLineRemove = line[3:].strip()
            # print("removed : " + strLineRemove)
            if patternLayout in line:
                modul = str(line[6:].split("/")[0])
                nameFileAfter = getFile(patternLayout, patternAfterFile, line, pathAfter)
            elif patternRemoveFile in line:

                isLineRemove = False

                if nameFileRemoved in tempNameFile:
                        isLineRemove = True
                        setRemovedFile.add(nameFileRemoved)
                        nameFileRemoved = ""
                elif modul in tempOnlyModul:
                        isLineRemove = True
                        setRemovedFile.add(nameFileRemoved)
                        nameFileRemoved = ""


        if(not isLineRemove):
            
            if(len(tempOnlyModul) == 0):
                if(nameFileCompare in tempNameFile):

                    isChangedId = False

                    if(patternBeforeIdView == line[:1]):
                        beforeNewId = getIdView(patternIdView, line)
                        if(beforeNewId.strip()):
                            setIdBefore.add(beforeNewId.strip())
                            # createFile(pathBefore, nameFileBefore, newId, modul)
                            # newId = ""
                            # print("before " + str(setIdBefore))
                                            
                    if(patternAfterIdView == line[:1]):
                        afterNewId = getIdView(patternIdView, line)
                        if(afterNewId.strip()):
                            setIdAfter.add(afterNewId.strip())
                            # createFile(pathAfter, nameFileAfter, newId, modul)
                            dictChangedId[beforeNewId] = afterNewId
                            afterNewId = ""
                            beforeNewId = ""
                            nameFileRemoved = ""
                            isChangedId = True

                    if(patternRemoveIdView == line[:1] and patternTagOpenIdView in line[1:] and not patternTagCloseFullIdView in line):
                        isRemovedId = True
                        nameView = getView(patternTagOpenIdView, line)
                        print "test cuy " + nameView
                    
                    if(patternBeforeIdView in line[:1] and nameView):
                        removeNewId = getIdView(patternIdView, line)
                        if(removeNewId.strip()):  
                            print "test " + removeNewId
                            setRemovedId.add(removeNewId)
                            nameView = ""
                            isRemovedId = False
                                
            
            elif(len(tempOnlyModul) > 0):
                # print(nameFileCompare)
                # isRemovedId = None

                if(modul in tempOnlyModul or nameFileCompare in tempNameFile):
                    
                    # print(isRemovedId)
                    isChangedId = False

                    if(patternBeforeIdView == line[:1]):
                        beforeNewId = getIdView(patternIdView, line)
                        if(beforeNewId.strip()):
                            setIdBefore.add(beforeNewId.strip())
                            # createFile(pathBefore, nameFileBefore, newId, modul)

                
                    if(patternAfterIdView == line[:1]):
                        afterNewId = getIdView(patternIdView, line)
                        
                        if(afterNewId.strip()):
                            setIdAfter.add(afterNewId.strip())
                            # createFile(pathAfter, nameFileAfter, newId, modul)
                            # dictChangedId[]
                            dictChangedId[beforeNewId] = afterNewId
                            afterNewId = ""
                            beforeNewId = ""
                            nameFileRemoved = ""
                            isChangedId = True
                            setRemovedId = set()
                            # print("after " + str(setIdAfter))
                    
                    # patternAA = "@@"
                    if(patternRemoveIdView == line[:1] and patternTagOpenIdView in line and not patternTagCloseFullIdView in line):
                        isRemovedId = True
                        nameView = getView(patternTagOpenIdView, line)
                        print "test cuy " + nameView
                    
                    if(patternBeforeIdView in line[:1] and nameView):
                        removeNewId = getIdView(patternIdView, line)
                        if(removeNewId.strip()):  
                            print "test " + removeNewId
                            setRemovedId.add(removeNewId)
                            nameView = ""
                            isRemovedId = False
                
                        if(nameFileCompare):
                            dictDeletedId[nameFileCompare] = setRemovedId

                    
        if(patternGitDiff in line[:10] and dictChangedId):
            dictBeforeChangedId = setDictIdView(dictBeforeChangedId, modul, nameFileBefore, dictChangedId)
            dictChangedId = dict()
            # setRemovedId = set()        
        # if(patternGitDiff in line[:10] and setIdAfter):
        #     dictAfterChangedId = setDictIdView(dictAfterChangedId, modul, nameFileAfter, setIdAfter)
        #     setIdAfter = set()
        
    print("dictDeleted " + str(dictDeletedId))
    # print("dictChange : " + str(dictBeforeChangedId))
    # print("after : " + str(dictAfterChangedId))
        
    
    endTime = time.time()
    print("Created file time : " + str(elapsedTime(starTime, endTime)) + " ms")

    # print(setRemovedFile)
    checkFileRemoved(setRemovedFile)
    checkChangedIdView(dictBeforeChangedId)
    checkIdRemoved(dictDeletedId)
    # setFileBefore = getModulFromPackage(pathBefore)
    # setFileAfter = getModulFromPackage(pathAfter)
    # checkIdView(setFileBefore, setFileAfter, beforePath, afterPath)


def setDictIdView(dictIdView, modul, nameFile, mapId):
    nameFileModul =  modul + "/" + getNameFileXML(nameFile)
    dictIdView[nameFileModul] = mapId
    return dictIdView

def checkIdRemoved(dictDeletedId):
    if len(dictDeletedId) > 0:
        print("\n====================================")
        print("The Id has been removed with name : ")
        
        for beforeKeyFile, beforeValueFile in dictDeletedId.items():
            print("\n" + beforeKeyFile)

            for value in beforeValueFile:
                print("(D) " + value)
        
        print("====================================")

def checkFileRemoved(setFile):
    if setFile:
        print("\n====================================")
        print("The Files has been removed with name : ")
        
        for f in setFile:
            print("\n (D) " + f)
        
        print("====================================")


def createDumpFile(tempName, input):
    with open(tempName, 'w') as file:
        file.write(input)

def elapsedTime(start, end):
    starTime = int(round(start * 1000))
    endTime = int(round(end * 1000))
    elapsedTime = endTime - starTime

    return elapsedTime

def removedPackFile(tempName, pathBefore, pathAfter):
    if os.path.exists(pathBefore):
        shutil.rmtree(pathBefore)
    if os.path.exists(pathAfter):
        shutil.rmtree(pathAfter)
    if os.path.exists(tempName):
        os.remove(tempName)

def checkChangedIdView(dictBeforeChangedId):
    starTime = None
    endTime = None

    starTime = time.time()

    if(len(dictBeforeChangedId) > 0):

        print("\n====================================")
        print("Below id has been changed/removed : ")
        
        for beforeKeyFile, beforeValueFile in dictBeforeChangedId.items():

            # beforeValue = sorted(beforeValue)
            # afterValue = sorted(afterValue)
            # print("before " + str(beforeValue))
            # print("after " + str(afterValue))

            for beforeValue, afterValue in beforeValueFile.items():

                if(beforeValue != afterValue):
                    print("\n" + beforeKeyFile + ":")
                    print("(M) " + beforeValue + " -> " + afterValue)

                # for beforeIdView in beforeValue:
                #     if beforeIdView not in afterValue:
                #         fileModule = beforeKey
                #         print("\n" + fileModule + ":")
                #         print(beforeIdView + " -> ")


    endTime = time.time()
    print("====================================")
        
    print("Checking id view time : " + str(elapsedTime(starTime, endTime)) + " ms")

    
def checkIdView(beforeFile, afterFile, beforePath, afterPath):
    starTime = None
    endTime = None
    setChangeId = set()

    starTime = time.time()
    if( len(beforeFile) > 0 and len(afterFile) > 0):

        if(len(beforeFile) == len(afterFile)):

            print("\n====================================")
            print("Below id has been changed/removed : ")

            for(before, after) in zip(beforeFile, afterFile):

                beforeSlash = before + "/"
                afterSlash = after + "/"

                beforeWithPath = getFileFromPackage(beforeSlash)
                afterWithPath = getFileFromPackage(afterSlash)

                # print(str(beforeWithPath))
                # print(str(afterWithPath))

                # print("before " + before)
                # print("after " + after)

                for(beforeFile, afterFile) in zip(beforeWithPath, afterWithPath):
                    
                    if(beforeFile == afterFile):

                        fileBeforePath = beforeSlash + beforeFile
                        fileAfterPath = afterSlash + afterFile

                        beforeF = open(fileBeforePath, "r")
                        afterF = open(fileAfterPath, "r")

                        setIdAfter = set()
                        setIdBefore = set()

                        for beforeLine in beforeF:
                            setIdBefore.add(getNameWithoutNewLine(beforeLine))
                            
                        for afterLine in afterF:
                            setIdAfter.add(getNameWithoutNewLine(afterLine))

                        for beforeIdView in setIdBefore:
                            if beforeIdView not in setIdAfter:
                                pathModule = fileBeforePath.split("/")[1]
                                fileModule = pathModule + "/" + getNameFileXML(beforeFile)
                                print("\n" + fileModule + ":")
                                print(beforeIdView + " -> ")
                            # print("id view " + beforeIdView + " has been changed or removed from file " +  fileModule)

                    # print(str(setIdBefore))
                    # print(str(setIdAfter))
    endTime = time.time()
    print("====================================")
        
    print("Checking id view time : " + str(elapsedTime(starTime, endTime)) + " ms")


def getNameWithoutNewLine(line):
    line = line.replace("\n", "")
    return line

def getFileWithPath(file, path):
    file = path + file
    return file

def getFileWithoutPath(file, path):
    file = file.replace(path, "")
    return file

def getFileFromPackage(modul):
    setFile = set()

    for getfile in os.listdir(modul):
        setFile.add(getfile)
    
    # print("file " + str(setFile))
    return setFile

def getModulFromPackage(path):
    # print(path)
    setModul = set()
    if os.path.exists(path):
        for filename in os.listdir(path):
            filePath = path + "/" + filename
            setModul.add(filePath)
    
    # print("package " + str(setModul))
    return setModul

def setValueFromFile(file):
    with open(file, "r") as fCase:
        strStrip = fCase.read().splitlines()
        tempCase = set(strStrip)
    
    # print(tempCase)
    return tempCase


def getNameFileXML(file):
    file = file.replace("txt", "xml")
    file = file.replace("before/", "")
    file = file.strip()

    return file

def notPatternBefore(line):
    if(line[:2] != "-+"):
        return True
    return False

def createFile(path, nameFile, input, modul):

    nameFileModul = path + "/" + modul + "/" + nameFile
    nameModul = path + "/" + modul

    if not os.path.exists(path):
        os.mkdir(path)
    
    if not os.path.exists(nameModul):
        os.mkdir(nameModul)

    if os.path.isfile(nameFileModul):
        with open(nameFileModul, 'a') as file:
                file.write("\n" + input)
    else:
        with open(nameFileModul, 'w') as fileW:
            fileW.write(input)
   
        # myFile = open(nameFile, 'w')
        # myFile.write(input)
        # myFile.close()

def getFile(patternLayout, patternFile, line, path):

    nameFile = ""
    posisiPattern = line.find(patternLayout)
    lengthPattern = len(patternLayout)
    posisiFile = posisiPattern + lengthPattern

    if patternFile is patternBeforeFile:
        nameFileBefore = line[posisiFile:]
        nameFile = nameFileBefore
    elif patternFile is patternAfterFile:
        nameFileAfter = line[posisiFile:]
        nameFile = nameFileAfter
    
    nameFile = nameFile.replace("xml", "txt")
    nameFile = nameFile.replace("\n", "")
    # print(nameFile)
    return nameFile

def getView(patternView, line):
    view = ""
    if (patternView in line):
        posisiPatternView = line.find(patternView)
        lengthPatternView = len(patternView)
        posisiView = posisiPatternView + lengthPatternView
        view = line[posisiView:]
    
    return view

def getIdView(patternIdView, line):
    newId = ""
    if patternIdView in line:
        posisiPatternIdView = line.find(patternIdView)
        lengthPatternIdView = len(patternIdView)
        posisiIdView = (posisiPatternIdView + lengthPatternIdView)
        nameIdView = line[posisiIdView:]
        nameLength = len(nameIdView)
        newId = nameIdView[:nameLength-2]
        # print(newId)
    return newId

# repo = git.Repo(os.getenv('GIT_REPO_PATH'))
# print repo.git.status()

if __name__ == "__main__":
    repo_path = os.getenv('GIT_REPO_PATH')
    # Repo object used to programmatically interact with Git repositories
    repo = Repo(repo_path)
    
    # check that the repository loaded correctly
    if not repo.bare:
        # print('Repo at {} successfully loaded.'.format(repo_path))
        # print_repository(repo)
        # create list of commits then print some of them to stdout
        # hcommit = repo.head.commit
        # prevHead = hcommit.diff('HEAD~1')
        # nowHead = hcommit.diff(None)
        # print(str(prevHead))
        # print(str(nowHead))

        # gitRepo = git.Repo(repo_path)
        # gitRepo.commits()

        # a_commit = gitRepo.commits()[0]
        # b_commit = gitRepo.commits()[1]

        # t = repo.head.commit.tree
        
        # print(a_commit)
        # print(b_commit)

        # repos = git.Repo(repo_path)
        # repos.git.commits()

        commits_list = list(repo.iter_commits())

        a_commit = commits_list[-1]
        b_commit = "a4186acd2ad2a077a5887f8ac16c50a37ca872fc"

        parser = optparse.OptionParser()
        parser.add_option('-d', '--default', dest='default', default="master", help="Set parameter default branch")
        parser.add_option('-t', '--target', dest='target', default=None, help="Set parameter target branch")

        (options, args) = parser.parse_args()

        targetBranch = ""
        defaultBranch = options.default
        if options.target:
            targetBranch = options.target

        # beforeC = repo.head.commit
        # afterC = "77a38266633ec3d224f5de799788adec94432492"
        
        starTime = time.time()

        writeFile(repo.git.diff(defaultBranch, targetBranch))

        endTime = time.time()

        print("Total time : " + str(elapsedTime(starTime, endTime)) + " ms")

    else:
        print('Could not load repository at {} :('.format(repo_path))