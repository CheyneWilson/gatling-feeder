plugins {
    idea
    scala
    signing
    `java-library`
    `maven-publish`
    id("com.palantir.git-version") version "0.12.3"
}

group = "nz.cheyne.gatling"

val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion()

repositories {
    mavenCentral()
}

dependencies {
    api("io.gatling:gatling-core:3.6.0")
    implementation("com.google.guava:guava:30.1.1-jre")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    testImplementation("org.scalatest:scalatest_2.13:3.2.9")
    testRuntimeOnly("co.helmethair:scalatest-junit-runner:0.1.9")
}

tasks.test {
    useJUnitPlatform() {
        includeEngines("scalatest")
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("gatlingFeeder") {
            from(components["java"])
            pom {
                name.set("Gatling Feeders")
                description.set("A library of reusable Gatling feeders")
                url.set("https://github.com/cheynewilson/gatling-feeder")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("cheynewilson")
                        name.set("Cheyne Wilson")
                        email.set("dev+gatling@cheyne.nz")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/cheynewilson/gatling-feeder.git")
                    developerConnection.set("scm:git:ssh://github.com/cheynewilson/gatling-feeder.git")
                    url.set("https://github.io/gatling-feeders/")
                }
            }
        }
    }
}

val signingKeyId: String? by project
val signingKey: String? by project
val signingPassword: String? by project

signing {
    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
    sign(publishing.publications["gatlingFeeder"])
}
