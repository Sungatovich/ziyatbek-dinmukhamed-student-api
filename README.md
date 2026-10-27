# Ziyatbek Dinmukhamed Student API

Personalized DevOps midterm project for **Ziyatbek Dinmukhamed**. It implements a Spring Boot REST CRUD API backed by PostgreSQL and includes Linux administration scripts, Git workflow instructions, Docker Compose, and a Jenkins CI pipeline.

## Technology

- Java 21, Spring Boot 3.4.5, Gradle
- PostgreSQL 16
- Docker multi-stage build and Docker Compose
- Jenkins Declarative Pipeline
- Linux Bash scripts

## Project structure

```text
ziyatbek-dinmukhamed-student-api/
├── scripts/
│   ├── Dinmukhamed_Ziyatbek_system_report.sh
│   └── analyze_log.sh
├── logs/
├── backups/
├── src/main/java/kz/ziyatbek/studentapi/
├── src/main/resources/application.properties
├── src/test/java/kz/ziyatbek/studentapi/
├── Dockerfile
├── docker-compose.yml
├── Jenkinsfile
├── build.gradle
├── settings.gradle
└── README.md
```

## 1. Linux administration

Run from the project directory:

```bash
mkdir -p scripts logs backups src
 touch README.md Dockerfile docker-compose.yml Jenkinsfile build.gradle
chmod 755 scripts/Dinmukhamed_Ziyatbek_system_report.sh
chmod 755 scripts/analyze_log.sh
./scripts/Dinmukhamed_Ziyatbek_system_report.sh
cp README.md backups/README_Dinmukhamed_Ziyatbek_backup.md
ls -l scripts logs backups
find . -maxdepth 4 -type f -not -path './.git/*' | sort
./scripts/analyze_log.sh
ps aux | grep -i '[j]ava' || true
tail -n 5 logs/Dinmukhamed_Ziyatbek_system_report.log
grep -in 'Dinmukhamed' logs/Dinmukhamed_Ziyatbek_system_report.log
```

The system-report script appends system information to the personal log. The script permission `755` gives the owner read/write/execute and group/others read/execute; if the instructor requires group/others read-only **without execute**, use `chmod 744 scripts/Dinmukhamed_Ziyatbek_system_report.sh` (owner `rwx`, group/others `r--`).

`>` creates or truncates a file before writing; `>>` appends without removing existing content.

## 2. Git and GitHub

Set your actual Git identity, then create the repository and initial commit:

```bash
git init
git config user.name "Ziyatbek Dinmukhamed"
git config user.email "YOUR_GITHUB_EMAIL"
git add .
git commit -m "Initial commit: Ziyatbek Dinmukhamed Student API"
git branch -M main
```

Feature branch and two meaningful commits:

```bash
git switch -c feature/ziyatbek-dinmukhamed-crud
# Make a code or documentation change
git add README.md
git commit -m "Document Ziyatbek CRUD API"
# Make another meaningful change, then:
git add src
git commit -m "Add student CRUD validation"
git switch main
git merge --no-ff feature/ziyatbek-dinmukhamed-crud
```

Conflict exercise (use a test branch, and keep a copy of README before editing):

```bash
git switch -c feature/ziyatbek-dinmukhamed-documentation
# Edit a README line and commit it
git add README.md && git commit -m "Update Ziyatbek API documentation"
# To create a real conflict, edit that same line differently on another branch,
# commit it there, then merge the two branches. Edit conflict markers manually.
git status
git add README.md
git commit -m "Resolve README documentation conflict"
git log --oneline --graph --decorate --all
```

Create an empty GitHub repository named `ziyatbek-dinmukhamed-student-api` in your account, then add its actual URL and push:

```bash
git remote add origin https://github.com/YOUR_GITHUB_USERNAME/ziyatbek-dinmukhamed-student-api.git
git push -u origin main
git push -u origin --all
git tag v1.0-ziyatbek-dinmukhamed
git push origin v1.0-ziyatbek-dinmukhamed
git remote -v
git branch -a
git tag -n
```

Never commit `.env`, passwords, access tokens or database credentials: repositories may be public or shared, and leaked credentials can be abused. Use environment variables or a secret store such as Jenkins Credentials.

## 3. Build and run

Requirements: Docker Engine and Docker Compose v2. Start both services:

```bash
docker compose up --build -d
docker compose ps
docker compose logs --tail=100 app
docker image ls ziyatbek-dinmukhamed-student-api
docker inspect ziyatbek-dinmukhamed-app
docker exec -it ziyatbek-dinmukhamed-app sh
docker compose stop app
docker compose start app
```

The API connects to host `db` (the Compose service name), not `localhost`. PostgreSQL data is stored in the named volume `ziyatbek-dinmukhamed-postgres-data`. To demonstrate persistence, create a record, restart the services, and retrieve the record again. Do not use `docker compose down -v` during this test because `-v` deletes the volume.

Default local demonstration credentials are provided for a classroom exercise only. For a real deployment, create a `.env` file with strong credentials and do not commit it. Example:

```bash
cat > .env <<'ENV'
POSTGRES_USER=studentsuser
POSTGRES_PASSWORD=replace_with_a_strong_local_password
ENV
chmod 600 .env
docker compose up --build -d
```

## 4. CRUD API testing

Base URL: `http://localhost:8080/api/students`

Create:

```bash
curl -i -X POST http://localhost:8080/api/students \
  -H 'Content-Type: application/json' \
  -d '{"name":"Ziyatbek Dinmukhamed","email":"ziyatbek@example.com","program":"DevOps"}'
```

List all, then use the returned `id` in the following requests:

```bash
curl -i http://localhost:8080/api/students
curl -i http://localhost:8080/api/students/1
curl -i -X PUT http://localhost:8080/api/students/1 \
  -H 'Content-Type: application/json' \
  -d '{"name":"Ziyatbek Dinmukhamed","email":"ziyatbek.updated@example.com","program":"Spring Boot and DevOps"}'
curl -i -X DELETE http://localhost:8080/api/students/1
curl -i http://localhost:8080/api/students/999999
```

Expected successful status codes: create `201`, list/get/update `200`, delete `204`. A nonexistent student returns `404`. Replace `/1` with the ID actually returned by your create request.

## 5. Tests and Jenkins

Run locally (requires Java 21 and Gradle installed):

```bash
gradle --no-daemon clean test bootJar
```

The tests use an in-memory H2 database, so they do not need a running PostgreSQL server. Jenkins requires a configured agent with Java 21, Gradle and Docker CLI/daemon access. Create a Jenkins Pipeline job named `Dinmukhamed_Ziyatbek_Student_API`, point it at this GitHub repository, and select “Pipeline script from SCM” with script path `Jenkinsfile`. Store private-repository credentials in Jenkins Credentials, not in this file.

The pipeline checks out the repository, builds, tests, creates the personalized Docker image and verifies it. A failing build or test stops later stages. Configure a GitHub webhook to your Jenkins URL, or use SCM polling if webhook access is not available. For a controlled failure demonstration, use a separate test branch, introduce a failing assertion, run the job, then revert/fix it and rerun.

## CI vs CD

- **Continuous Integration (CI):** frequently integrate code and automatically build/test it.
- **Continuous Delivery:** keep validated changes ready for release; production release may require approval.
- **Continuous Deployment:** automatically release every change that passes the pipeline to production.

## Important evidence to capture for submission

This starter project cannot create screenshots or a GitHub repository in your account on your behalf. Capture your own terminal output for: project tree, script/log, permissions, backup contents, Git history/branches/tag/remote, Docker image and running containers, API CRUD responses, Jenkins successful and intentionally failed builds, and automatic trigger. The assignment requires this evidence as part of the demonstration.
## Student Information

- Student: Ziyatbek Dinmukhamed
<<<<<<< HEAD
- Project: Student API — Ziyatbek Dinmukhamed
=======
- Project: Student API — Documentation Branch
>>>>>>> feature/dinmukhamed-ziyatbek-documentation
- Feature branch: feature/dinmukhamed-ziyatbek-crud

## Individual CRUD Improvement

This feature implements and documents CRUD operations for student records.
The API supports creating, retrieving, updating, and deleting students.
Requests for nonexistent student IDs should return an appropriate HTTP error.
