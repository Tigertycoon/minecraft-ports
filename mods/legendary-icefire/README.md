# legendary-icefire

Unofficial Minecraft 1.21.1 / NeoForge port of [TheMuluk/Legendary-Icefire-Integration-1.20.1-Forge](https://github.com/TheMuluk/Legendary-Icefire-Integration-1.20.1-Forge).

## Port contribution

The port migrates registration and client item properties to NeoForge, adapts canteen/healing APIs and uses the singular `recipe` and `tags/item` resource directories introduced in Minecraft 1.21.

## Dependency profile

Legendary Survival Overhaul 2.4.5; runtime requires Ice and Fire CE 2.0-beta.17 or later. Build from the collection root with `./gradlew :legendary-icefire:build`. Dependency JARs are downloaded, not bundled.

## Provenance and license

Reference source: `2ce455db3de9743bb79b8fda259927ccdda34118`. Original authorship is retained in the packages, source notices and metadata. Port maintenance: Niklas / Tigertycoon. License: [GPL-3.0](LICENSE).

The source compiles without an Ice and Fire JAR because that integration uses resource identifiers. Runtime evaluation still requires the matching Ice and Fire CE build. This repository does not bundle it.
