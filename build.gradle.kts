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

val osName = System.getProperty("os.name")!!
val currentPlatform = when {
    osName.matches(Regex(".*Mac.*")) -> "macosx-x86_64"
    osName.matches(Regex(".*Linux.*")) -> "linux-x86_64"
    osName.matches(Regex(".*Windows.*")) -> "windows-x86_64"
    else -> ""
}

dependencies {
    implementation("org.scala-lang:scala3-library_3:3.3.1")
    implementation("dev.storch:core_3:0.0-05078a8-SNAPSHOT")

    implementation(group = "org.bytedeco",
        name = "pytorch",
        version = "2.0.1-1.5.10-20230612.164657-2",
        classifier = currentPlatform)
    implementation(group = "org.bytedeco",
        name = "pytorch-platform",
        version = "2.1.0-1.5.10-SNAPSHOT")

    implementation(group = "org.bytedeco",
        name = "openblas",
        version = "0.3.23-1.5.10-SNAPSHOT",
        classifier = currentPlatform)
    implementation(group = "org.bytedeco",
        name = "openblas-platform",
        version = "0.3.24-1.5.10-SNAPSHOT")

    if(currentPlatform.matches(Regex(".*windows.*|.*linux.*"))){
        implementation(group = "org.bytedeco",
            name = "cuda",
            version = "12.3-8.9-1.5.10-20231025.123723-1",
            classifier = currentPlatform)
        implementation(group = "org.bytedeco",
            name = "cuda",
            version = "12.3-8.9-1.5.10-20231025.123723-1",
            classifier = "${currentPlatform}-redist")
    }

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("runCustomNN"){
    mainClass.set("it.unibo.CustomNN")
    jvmArgs(
        "-XX:+CreateCoredumpOnCrash"
    )
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<JavaExec>("runBasic"){
    mainClass.set("it.unibo.BasicExample")
    jvmArgs(
        "-XX:+CreateCoredumpOnCrash"
    )
    classpath = sourceSets["main"].runtimeClasspath
}


/*
tasks.withType<JavaExec> {
    jvmArgs(
        "-XX:+CreateCoredumpOnCrash"
    )
    println("HEY ${name}")
}
*/