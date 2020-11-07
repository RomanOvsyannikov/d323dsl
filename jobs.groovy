job ("MNTLAB-rovsyannikov-main-build-job") {
}

for (i in (1..4)) {
    job("MNTLAB-rovsyannikov-child${i}-build-job") {
    }
}
