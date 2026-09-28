# mekvamp

Unofficial Minecraft 1.21.1 / NeoForge port of [DogGirl/MinecraftMekanismVampirismIntegration](https://github.com/DogGirl/MinecraftMekanismVampirismIntegration).

## Port contribution

The port adapts NeoForge registration/events, Mekanism module APIs and Vampirism integration. Publication repairs preserve sub-point blood remainders and replace emptied containers using the fluid capability result.

## Dependency profile

Mekanism 10.7.19.85 and Vampirism 1.10.8. Build from the collection root with `./gradlew :mekvamp:build`. Dependency JARs are downloaded, not bundled.

## Provenance and license

Reference source: `1b9a53d93ef97192c8557c4cddab68d3da90ab31`. Original authorship is retained in the packages, source notices and metadata. Port maintenance: Niklas / Tigertycoon. License: [CC-BY-NC-SA-4.0](LICENSE).

Upstream metadata and the local port incorrectly stated MIT. The upstream LICENCE is retained as the governing component license.
