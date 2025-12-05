plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    signing
}

android {
    namespace = "com.nukkadshops.pinelabs"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

publishing {

    publications {
        create<MavenPublication>("release") {

            // Maven Central coordinates
            groupId = "io.github.linga-sunvith"
            artifactId = "pinelabs"
            version = "1.0.0"

            // Publish AAR
            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("Pinelabs SDK")
                description.set("Payment integration SDK for PineLabs Android terminals.")
                url.set("https://github.com/Linga-Sunvith/payment-integration")

                licenses {
                    license {
                        name.set("Apache 2.0 License")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                }

                scm {
                    url.set("https://github.com/Linga-Sunvith/payment-integration")
                    connection.set("scm:git:git://github.com/Linga-Sunvith/payment-integration.git")
                    developerConnection.set("scm:git:ssh://github.com:Linga-Sunvith/payment-integration.git")
                }

                developers {
                    developer {
                        id.set("linga-sunvith")
                        name.set("Sunvith")
                        email.set("sunvithlinga22@gmail.com") // optional
                    }
                }
            }
        }
    }

    repositories {
        maven {
            name = "sonatype"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        System.getenv("GPG_SIGNING_KEY"),
        System.getenv("GPG_SIGNING_PASSWORD")
    )

    sign(publishing.publications["release"])
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    testImplementation(libs.junit)
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.12.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
