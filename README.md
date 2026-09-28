# Minecraft ports

[![Build and test](https://github.com/Tigertycoon/minecraft-ports/actions/workflows/build.yml/badge.svg)](https://github.com/Tigertycoon/minecraft-ports/actions/workflows/build.yml)

Three small integration mods adapted for **Minecraft 1.21.1 / NeoForge**. Each project retains its upstream authors, origin and license. This collection highlights the migration and maintenance work by Niklas / Tigertycoon.

| Project | Integration | Work in this port | License |
| --- | --- | --- | --- |
| [SandFilter](mods/sandfilter) | Create + Legendary Survival Overhaul | Fluid capabilities, persistent/synchronized purity components, Ponder API and mixins | MIT |
| [Mekvamp](mods/mekvamp) | Mekanism + Vampirism | NeoForge events, module APIs and blood-container handling | CC-BY-NC-SA-4.0 |
| [Legendary Icefire](mods/legendary-icefire) | Legendary Survival Overhaul + Ice and Fire CE | Registration, item APIs and 1.21 resource paths | GPL-3.0 |

## Build

Install Java **21**. No dependency JARs or neighboring checkouts are required for compilation.

```sh
./gradlew build --no-daemon
./gradlew :sandfilter:build
./gradlew :mekvamp:build
./gradlew :legendary-icefire:build
```

On Windows use `gradlew.bat`. Each mod writes its own JARs under `mods/<project>/build/libs/`; install only the integrations you need, together with their required mods.

## What is checked

All three mods compile and package on the pinned NeoForge **21.1.236** toolchain. SandFilter has real mod-runtime tests for fluid purity persistence and bottle filling. Mekvamp has runtime registration and fluid-consumption regression tests. Legendary Icefire is compile/resource checked; its Ice and Fire CE gameplay remains a manual validation step. See [validation](docs/validation.md).

Larger related work lives in [Matter Megadrive](https://github.com/Tigertycoon/matter-megadrive) and [Better Mob Combat](https://github.com/Tigertycoon/better-mob-combat). Original integrations are in [minecraft-integrations](https://github.com/Tigertycoon/minecraft-integrations).

## Attribution

These are ports of existing mods, with separate component licenses. **There is no blanket MIT license for this collection.** See [LICENSES.md](LICENSES.md) and the origin record in each project. Publication cleanup and checks were prepared with assistance from Codex.
