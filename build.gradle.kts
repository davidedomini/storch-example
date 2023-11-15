import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

plugins {
    id("java")
    id("scala")
}

scala {
    zincVersion.set("1.6.1")
}

sourceSets {
    main {
        scala {
            setSrcDirs(listOf("src/main/scala"))
        }
    }
    test {
        scala {
            setSrcDirs(listOf("src/test/scala"))
        }
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    implementation("org.scala-lang:scala3-library_3:3.3.1")
    implementation("dev.storch:core_3:0.0-05078a8-SNAPSHOT")
    implementation(group = "org.bytedeco",
        name = "pytorch-platform",
        version = "2.0.1-1.5.10-SNAPSHOT",
        classifier = "linux-x86_64")
    implementation(group = "org.bytedeco",
        name = "cuda-platform-redist",
        version = "12.1-8.9-1.5.10-SNAPSHOT",
        classifier = "linux-x86_64")
    //implementation("org.bytedeco:pytorch-platform:2.0.1-1.5.10-SNAPSHOT")
    //implementation("org.bytedeco:cuda-platform-redist:12.1-8.9-1.5.10-SNAPSHOT")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}