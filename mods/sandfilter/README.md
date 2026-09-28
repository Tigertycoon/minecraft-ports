# sandfilter

Unofficial Minecraft 1.21.1 / NeoForge port of [DeeChael/SandFilter](https://github.com/DeeChael/SandFilter).

## Port contribution

The port replaces NBT fluid tags with a persistent, network-synchronized data component; adapts fluid capabilities and Ponder; explicitly enables mixins; and uses the NeoForge creative-tab event instead of injecting into an unstable compiler-generated lambda. Jade support is optional and client-side.

## Dependency profile

Create 6.0.10 and Legendary Survival Overhaul 2.4.5. Build from the collection root with `./gradlew :sandfilter:build`. Dependency JARs are downloaded, not bundled.

## Provenance and license

Reference source: `bd9bd72e26bbf799de8888408c7a154fecc9532e`. Original authorship is retained in the packages, source notices and metadata. Port maintenance: Niklas / Tigertycoon. License: [MIT](LICENSE).

The sand filter model and texture originate from [Thirst Was Taken](https://modrinth.com/mod/thirst-was-taken), as credited by the original SandFilter author.

This source tree supplies no crafting recipe for the filter block. It is available in the creative tab; a survival modpack must provide its intended acquisition recipe.
