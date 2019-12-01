import os
import shutil
import os.path
import git
import itertools
from git import Repo

COMMITS_TO_PRINT = 5
patternLayout = "res/layout/"
patternIdView = "android:id=\"@+id/"
patternBeforeFile = "---"
patternAfterFile = "+++"
patternBeforeIdView = "-"
patternAfterIdView = "+"
patternRemoveFile = "/dev/null"
beforePath = "before/"
afterPath = "after/"
pathBefore = "before"
pathAfter = "after"

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

# def print_diff(commitA, commitB):
#     print('-----')

def writeFile(input):
    # fileName = "/temp/input.txt"

    tempName = "dump_input.txt"
    testCase = "test_case.txt"

    if not os.path.exists(tempName):
        createDumpFile(tempName, input)
    # elif os.path.exists(tempName):
    #     removedPackFile()
    #     createDumpFile(tempName, input)


    tempStr = ""

    inputLine = ""

    nameFileAfter = ""
    nameFileBefore = ""
    nameFileCompare = ""

    setIdAfter = set()
    setIdBefore = set()

    newId = ""

    fileCase = None

    if(os.path.exists(testCase)):
        with open(testCase, "r") as fCase:
            strStrip = fCase.read().splitlines()
            tempCase = set(strStrip)
            print("tempCase: " + str(tempCase))


    f = open(tempName, "r")
    for line in f:
        if(patternBeforeFile in line[:3]):
            if patternLayout in line:
                nameFileBefore = getFile(patternLayout, patternBeforeFile, line, pathBefore)
                nameFileCompare = getNameFileXML(nameFileBefore)
        
        if(patternAfterFile in line[:3]):
            if patternLayout in line:
                nameFileAfter = getFile(patternLayout, patternAfterFile, line, pathAfter)
            elif patternRemoveFile in line:
                if nameFileCompare not in tempCase:
                        print("Previous file has removed with name : " + nameFileCompare)
                        nameFileCompare = ""

        if(nameFileCompare in tempCase):
            if(patternBeforeIdView == line[:1] and notPatternBefore(line)):
                newId = getIdView(patternIdView, line)
                if(newId.strip()):
                    setIdBefore.add(newId.strip())
                    createFile(pathBefore, nameFileBefore, newId)
                    newId = ""
        
            if(patternAfterIdView == line[:1]):
                newId = getIdView(patternIdView, line)
                if(newId.strip()):
                    setIdAfter.add(newId.strip())
                    createFile(pathAfter, nameFileAfter, newId)
                    newId = ""

        
    setFileBefore = getFileFromPackage(pathBefore)
    setFileAfter = getFileFromPackage(pathAfter)

    checkIdView(setFileBefore, setFileAfter, beforePath, afterPath)


def createDumpFile(tempName, input):
    with open(tempName, 'w') as file:
        file.write(input)
    # myFile = open(tempName, 'w')
    # myFile.close()

def removedPackFile():
    shutil.rmtree("after")
    shutil.rmtree("before")
    os.remove("dump_input.txt")
    
def checkIdView(beforeFile, afterFile, beforePath, afterPath):

    if(len(beforeFile) == len(afterFile)):

        for(before, after) in zip(beforeFile, afterFile):
            beforeX = getFileWithoutPath(before, beforePath)
            afterX = getFileWithoutPath(after, afterPath)

            # print(beforeX)
            # print(afterX)
            if(beforeX == afterX):
                beforeWithPath = getFileWithPath(beforeX, beforePath)
                afterWithPath = getFileWithPath(afterX, afterPath)

                beforeF = open(beforeWithPath, "r")
                afterF = open(afterWithPath, "r")

                setIdAfter = set()
                setIdBefore = set()

                for beforeLine in beforeF:
                    setIdBefore.add(getNameWithoutNewLine(beforeLine))
                
                for afterLine in afterF:
                    setIdAfter.add(getNameWithoutNewLine(afterLine))

                # print(str(setIdBefore))
                # print(str(setIdAfter))

                for beforeIdView in setIdBefore:
                    if beforeIdView not in setIdAfter:
                        print("id view " + beforeIdView + " has been changed or removed from file " + getNameFileXML(beforeX))

                # print(str(setIdBefore))
                # print(str(setIdAfter))

def getNameWithoutNewLine(line):
    line = line.replace("\n", "")
    return line

def getFileWithPath(file, path):
    file = path + file
    return file

def getFileWithoutPath(file, path):
    file = file.replace(path, "")
    return file

def getFileFromPackage(path):
    setFile = set()
    for filename in os.listdir(path):
        filePath = path + "/" + filename
        setFile.add(filePath.strip())
    
    print(setFile)
    return setFile

def setValueFromFile(file):
    with open(file, "r") as fCase:
        strStrip = fCase.read().splitlines()
        tempCase = set(strStrip)
    
    print(tempCase)
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

def createFile(path, nameFile, input):
    if not os.path.exists(path):
           os.mkdir(path)

    if os.path.isfile(nameFile):
        with open(nameFile, 'a') as file:
            file.write("\n" + input)
    else:
        with open(nameFile, 'w') as fileW:
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
        pathBefore = path
        nameFile = pathBefore + "/" + nameFileBefore
    elif patternFile is patternAfterFile:
        nameFileAfter = line[posisiFile:]
        pathAfter = path
        nameFile = pathAfter + "/" + nameFileAfter
    
    nameFile = nameFile.replace("xml", "txt")
    nameFile = nameFile.replace("\n", "")
    return nameFile


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

# def checkIdView():

# repo_path = os.getenv('GIT_REPO_PATH')
# print_repository(repo_path)

# repo = git.Repo(os.getenv('GIT_REPO_PATH'))
# print repo.git.status()

if __name__ == "__main__":
    repo_path = os.getenv('GIT_REPO_PATH')
    # Repo object used to programmatically interact with Git repositories
    repo = Repo(repo_path)

    defaultBranch = "master"
    
    # check that the repository loaded correctly
    if not repo.bare:
        print('Repo at {} successfully loaded.'.format(repo_path))
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

        # beforeC = repo.head.commit
        # afterC = "77a38266633ec3d224f5de799788adec94432492"

        writeFile(str(repo.git.diff('master', 'sukasuka')))
        # writeFile(repo.git.diff("HEAD","HEAD~1"))
        # checkIdView()

        # pass
        # commits = list(repo.iter_commits('master'))[:COMMITS_TO_PRINT]
        # for commit in commits:
        #     print_commit(commit)
        #     # print_diff
        #     pass
    else:
        print('Could not load repository at {} :('.format(repo_path))