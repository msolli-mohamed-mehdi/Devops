def getGitBranchName() {  
    return scm.branches[0].name
}

def branchName
def targetBranch



pipeline {
  agent any
	environment {
     DOCKERHUB_USERNAME = "MohamedMehdiMsolli"
     PROD_TAG = "${DOCKERHUB_USERNAME}/eventsproject:v1.0-prod"
    }
	parameters {
	string(name: 'BRANCH_NAME', defaultValue: "${scm.branches[0].name}", description: 'Git branch name')
        string(name: 'CHANGE_ID', defaultValue: '', description: 'Git change ID for merge requests')
	string(name: 'CHANGE_TARGET', defaultValue: '', description: 'Git change ID for the target merge requests')
    }

  stages {
    stage('Github') {
      steps {
	script {
	branchName = params.BRANCH_NAME
        targetBranch = branchName

          git branch: branchName,
          url: 'https://github.com/msolli-mohamed-mehdi/Devops.git',
          credentialsId: 'c2de7031-619a-4ee3-a1c9-98c2c6262dbd'
      }
	  echo "Current branch name: ${branchName}"
	  echo "Current branch name: ${targetBranch}"
      }
    }

	  stage('MVN BUILD') {
      when {
        expression {
          (params.CHANGE_ID != null) && ((targetBranch == 'master'))
        }
      }
      steps {
        sh 'mvn clean install'
        echo 'Build stage done'
      }
    }

stage('MVN COMPILE') {
      when {
        expression {
          (params.CHANGE_ID != null) && ((targetBranch == 'master'))
        }
      }
      steps {
       
	sh 'mvn compile'
        echo 'Compile stage done'
      }
    }

stage ('JUNIT TEST') {
	when {
         expression {
           (params.CHANGE_ID != null) && ((targetBranch == 'master'))
            }
	   }
      steps {
        sh 'mvn test'
        echo 'test stage done'
      }
    }



	stage ('STATIC TEST WITH SONAR') {
       when {
         expression {
           (params.CHANGE_ID != null) && ((targetBranch == 'master'))
         }
       }
       steps {
         withSonarQubeEnv('sonarqube') {
                sh 'mvn sonar:sonar'
         }
       }
    }

	   stage ('NEXUS DEPLOY') {
	when {
         expression {
          (params.CHANGE_ID != null) && ((targetBranch == 'master'))
	}
	   }
       steps {
       sh 'mvn deploy -DskipTests'
      }
    }


	  
	stage('Build Docker') {
    when {
        expression {
          (params.CHANGE_ID != null) && ((targetBranch == 'master'))
        }
    }
    steps {
        script {
            if (targetBranch == 'master') {
                sh "docker build -t ${PROD_TAG} ."
            } 
        }
    }
}



	  stage('Docker Login'){
	     when {
        expression {
          (params.CHANGE_ID != null) && ((targetBranch == 'master'))
        }
    }
            steps{
                withCredentials([usernamePassword(credentialsId: '5cbdeb85-0052-4996-8a99-0fa003e7319f	', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                sh "docker login -u ${DOCKERHUB_USERNAME} -p ${DOCKERHUB_PASSWORD}"
    }
  }

        }



	// stage('Docker Push'){
	// 	when {
 //        expression {
 //          (params.CHANGE_ID != null) && ((targetBranch == 'Categorie_Produit'))
 //        }
 //    }
 //            steps{
 //                sh 'docker push $DOCKERHUB_USERNAME/achat --all-tags '
 //            }
 //        }



	  

	  
  }
}
