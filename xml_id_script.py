import os
import os.path
import git
from git import Repo

COMMITS_TO_PRINT = 5
patternLayout = "res/layout/"
patternIdView = "android:id=\"@+id/"
patternBeforeFile = "---"
patternAfterFile = "+++"
patternBeforeIdView = "-"
patteernAfterIdView = "+"

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

    if not os.path.exists(tempName):
        myFile = open(tempName, 'w+')
        myFile.write(input)
        myFile.close()

    tempStr = ""

    inputLine = ""

    nameFileAfter = ""
    nameFileBefore = ""

    f = open(tempName, "r")
    for line in f:
        if(patternBeforeFile in line[:3]):
            if patternLayout in line:
                pathBefore = "before"
                nameFileBefore = getFile(patternLayout, patternBeforeFile, line, pathBefore)
        
        if(patternAfterFile in line[:3]):
            if patternLayout in line:
                pathAfter = "after"
                nameFileAfter = getFile(patternLayout, patternAfterFile, line, pathAfter)

        if(patternBeforeIdView in line[:1]):
            newId = getIdView(patternIdView, line)
            createFile(pathBefore, nameFileBefore, newId)
        
        if(patteernAfterIdView in line[:1]):
            newId = getIdView(patternIdView, line)
            createFile(pathAfter, nameFileAfter, newId)

def createFile(pathBefore, nameFile, input):
    if not os.path.exists(pathBefore):
           path = os.mkdir(pathBefore)

    if os.path.isfile(nameFile):
        with open(nameFile, 'a') as file:
            file.write(input)
    else:
        myFile = open(nameFile, 'w')
        myFile.write(input)
        myFile.close()

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
        print(newId)

    return newId

# repo_path = os.getenv('GIT_REPO_PATH')
# print_repository(repo_path)

# repo = git.Repo(os.getenv('GIT_REPO_PATH'))
# print repo.git.status()

if __name__ == "__main__":
    repo_path = os.getenv('GIT_REPO_PATH')
    # Repo object used to programmatically interact with Git repositories
    repo = Repo(repo_path)
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

        t = repo.head.commit.tree
        
        # print(a_commit)
        # print(b_commit)

        # print(repo.git.diff('HEAD~1'))

        # print(repo.git.diff('HEAD~1'))

        writeFile(str(repo.git.diff('HEAD~1')))

        # pass
        # commits = list(repo.iter_commits('master'))[:COMMITS_TO_PRINT]
        # for commit in commits:
        #     print_commit(commit)
        #     # print_diff
        #     pass
    else:
        print('Could not load repository at {} :('.format(repo_path))