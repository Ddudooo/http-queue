rootProject.name = "http-queue"
include("persistence")
include("core")
include("adapter")
include("adapter:slack")
findProject(":adapter:slack")?.name = "slack"
include("adapter:web")
findProject(":adapter:web")?.name = "web"
include("adapter:web:api")
findProject(":adapter:web:api")?.name = "api"
include("persistence:database")
findProject(":persistence:database")?.name = "database"
include("core:common")
findProject(":core:common")?.name = "common"
include("core:common:enum")
findProject(":core:common:enum")?.name = "enum"
include("core:common:error")
findProject(":core:common:error")?.name = "error"
include("core:domain")
findProject(":core:domain")?.name = "domain"
include("core:domain:queue")
findProject(":core:domain:queue")?.name = "queue"
include("application")
