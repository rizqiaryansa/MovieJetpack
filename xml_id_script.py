import os
import git
from git import Repo

COMMITS_TO_PRINT = 5

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

    patternStr = "--- a/app/src/main/res/layout/item_genre.xml"

    patternStr = patternStr.replace("--- a/app/src/main/res/layout/", "")
    patternStr = patternStr.replace("xml", "txt")

    # for tmpStr: input:


    # if(pattern == myFile):
    #     name = myFile
    #     fileName = "/temp/"


    myFile = open(patternStr, 'w')

    myFile.write(input)
    myFile.close()

def getIdView(input):

    tmpStr = ""
    patternIdView = "android:id="@+id/"

    for(line in input):
        if(line == patternIdView):
            tempStr.append(line)


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

        # print(repo.git.diff(t))

        print(repo.git.diff('HEAD~1'))

        writeFile(repo.git.diff('HEAD~1'))



        # pass
        # commits = list(repo.iter_commits('master'))[:COMMITS_TO_PRINT]
        # for commit in commits:
        #     print_commit(commit)
        #     # print_diff
        #     pass
    else:
        print('Could not load repository at {} :('.format(repo_path))