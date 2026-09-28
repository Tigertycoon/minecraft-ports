# Validation

- All three projects: Java 21 compilation, resource expansion, separate mod/source JARs.
- SandFilter: three tests in NeoForge with Create 6.0.10 and Legendary Survival Overhaul 2.4.5. Purified fluid survives serialization; filling the last 250 mB produces a purified bottle; ordinary water remains an ordinary potion. The filling checks execute Create's actual mixin-transformed method.
- Mekvamp: both integration items registered with Mekanism/Vampirism loaded; partial blood quantities are preserved, drainage is bounded by the deficit, and unrelated fluids are ignored.
- Legendary Icefire: compilation and resource checks. Ice and Fire CE gameplay and loaded recipe resolution are not yet validated here.

Mekvamp's registration tests use the initialized mod environment without starting NeoForge's ephemeral world: Vampirism expects additional dimensions that this fixture does not create. This is not a full gameplay test.

Client rendering, Ponder demonstrations, multiplayer interaction and full modpack playthroughs remain manual checks. A green build does not establish those results.
