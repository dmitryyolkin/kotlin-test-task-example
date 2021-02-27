# Environment

The project was implemented with:
* open JDK 1.8 (from [amazon-corretto](https://docs.aws.amazon.com/corretto/latest/corretto-8-ug/downloads-list.html))
  * it was tested internally for JDK 11 as well but as long as it was said in task description to show best knowledge of jdk 8 - I've decided to keep JDK 8 as default target version
* Kotlin 1.4.10
* Gradle (6.7) based on Kotlin DSL
* JUnit 5.7.1

# Implementation details
* All project dependencies are specified at `Config.kt`
* Transactions are loaded from CSV file in `TransactionLoader` class with help of [opencsv](https://www.baeldung.com/opencsv) lib
* Transactions are analyzed at `TransactionAnalyzer` class. There are several tricks to improve performance:
  * there is `merchant2Transactions` HashMap to map merchants to their transactions (it reduces iterations count)
  * there is `reversalTransactions` HashSet where all reversed transaction ids are stored (to exclude them from results)
  * as it was said that for the sake of simplicity, we can assume that Transaction records are listed in correct time order - we stop iterating through transactions list if a last processed transaction `date` is after passed `toDate`
    * it helps to return result faster on big amount of data
  * Result is returned as a Pair of values where
    * first element is the total number of transactions
    * second element is and the average transaction value for a specific merchant in a specific date range

# Tests
Tests were implemented with `junit 5.7.1` and put into `TransactionAnalyzerUTest`
* if you want to add a new test you can add it into `providerData` method -> `hoolah/yolkin/challenge/TransactionAnalyzerUTest.kt:44`
* test CVS file can be put into `src/test/resources`
  * default `transactions.csv` sample from task description is already there

# Production mode
As it was said that code should be ready to production usage then some steps were implemented:
* add main method in `TransactionAnalyzer` -> `hoolah/yolkin/challenge/TransactionAnalyzer.kt:86`
* add `fatJar` task into `build.gradle.kts` to build fat jar with all dependencies and main class specified -> `build.gradle.kts:17`
* as well Dockerfile was described -> `Dockerfile:1` - it simplifies app start in production

Next steps should be done to build `fatJar` and Docker container:
* move to project root 
* execute from terminal `./gradlew clean build fatJar`
  * it cleans previous artifacts (if you have), 
  * run kotlin linter
  * run unit tests
  * build `hoolah.jar` and `hoolah-fat.jar` -> look up artifacts at `./build/libs`
* If you don't need Docker container then you can run jar as follows:
  * make sure you're in the project root dir
  * execute in terminal ` java -jar ./build/libs/hoolah-fat.jar ./src/test/resources/transactions.csv "20/08/2020 12:00:00" "20/08/2020 13:00:00" "Kwik-E-Mart"`
  * as a result you will see in terminal expected result

## Docker run
Docker is a standard way to run apps in production mode nowadays.
So we can build Docker image and run container:
* make sure you have Docker Desktop installed on you PC - you can get it [here](https://www.docker.com/products/docker-desktop)
* make sure you're in the project root dir
* build docker image as `docker build -t hoolah .`
* run docker container
  * you can mount some external dir to use CSV files from there
  * e.g. if you're in project root dir you can use test file from `src/test/resources`
  * run in terminal `docker run --mount type=bind,source="$(pwd)"/src/test/resources,target=/app -it hoolah /app/transactions.csv '20/08/2020 12:00:00' '20/08/2020 13:00:00' 'Kwik-E-Mart'`
  * as a result you will see in terminal expected result 