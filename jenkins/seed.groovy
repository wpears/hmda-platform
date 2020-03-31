projects = [
    [name: "auth", repo: "hmda-platform", jenkinsfilePath: "auth/Jenkinsfile"],
    [name: "census-api", repo: "hmda-platform", jenkinsfilePath: "census-api/Jenkinsfile"],
    [name: "check-digit", repo: "hmda-platform", jenkinsfilePath: "check-digit/Jenkinsfile"],
    [name: "email-service", repo: "hmda-platform", jenkinsfilePath: "email-service/Jenkinsfile"],
    [name: "hmda-analytics", repo: "hmda-platform", jenkinsfilePath: "hmda-analytics/Jenkinsfile"],
    [name: "hmda-data-browser-api", repo: "hmda-platform", jenkinsfilePath: "data-browser/Jenkinsfile"],
    [name: "hmda-documentation", repo: "hmda-documentation", jenkinsfilePath: "Jenkinsfile"],
    [name: "hmda-frontend", repo: "hmda-frontend", jenkinsfilePath: "Jenkinsfile"],
    [name: "hmda-help", repo: "hmda-help", jenkinsfilePath: "Jenkinsfile"],
    [name: "hmda-platform", repo: "hmda-platform", jenkinsfilePath: "hmda/Jenkinsfile"],
    [name: "hmda-data-publisher", repo: "hmda-platform", jenkinsfilePath: "hmda-data-publisher/Jenkinsfile"],
    [name: "hmda-reporting", repo: "hmda-platform", jenkinsfilePath: "hmda-reporting/Jenkinsfile"],
    [name: "hmda-spark-reporting", repo: "hmda-platform", jenkinsfilePath: "hmda-spark-reporting/Jenkinsfile"],
    [name: "keycloak", repo: "hmda-platform", jenkinsfilePath: "kubernetes/keycloak/Jenkinsfile"],
    [name: "institutions-api", repo: "hmda-platform", jenkinsfilePath: "institutions-api/Jenkinsfile"],
    [name: "irs-publisher", repo: "hmda-platform", jenkinsfilePath: "irs-publisher/Jenkinsfile"],
    [name: "modified-lar", repo: "hmda-platform", jenkinsfilePath: "modified-lar/Jenkinsfile"],
    [name: "rate-limit", repo: "hmda-platform", jenkinsfilePath: "rate-limit/Jenkinsfile"],
    [name: "ratespread-calculator", repo: "hmda-platform", jenkinsfilePath: "ratespread-calculator/Jenkinsfile"],
    [name: "theme-provider", repo: "hmda-platform", jenkinsfilePath: "kubernetes/keycloak/theme-provider/Jenkinsfile"]
]

projects.each { project ->
    multibranchPipelineJob(project.name) {
        branchSources {
            github {
                id('hmda')
                repoOwner('cfpb')
                repository(project.repo)
                scanCredentialsId('009c8c9d-3cf5-4b2a-89f3-286977cabddf')
                buildForkPRHead(true)
                buildForkPRMerge(false) 
            }
            orphanedItemStrategy {
                discardOldItems {
                    daysToKeep(1)
                }
            }
            factory {
                workflowBranchProjectFactory {
                    scriptPath(project.jenkinsfilePath)
                }
            }
            triggers {
                periodic(30)
            }
        }
    }
}
