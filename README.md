ExtraGamerules  
[![Maven Repository](https://img.shields.io/maven-metadata/v/https/maven.misterpemodder.com/list/libs-release/com/misterpemodder/extra-gamerules/maven-metadata.xml.svg)](https://maven.misterpemodder.com/libs-release/com/misterpemodder/extra-gamerules)
[![](http://cf.way2muchnoise.eu/full_310323_downloads.svg)](https://minecraft.curseforge.com/projects/extragamerules)
[![](http://cf.way2muchnoise.eu/versions/For%20MC_310323_all.svg)](https://minecraft.curseforge.com/projects/extragamerules)
=========================

This mod adds a bunch a new gamerules and some from the bedrock edition of the game.

#### Gamerules:

- lightningProbablity: `/gamerule lightningProbability <integer>`  
This number means there is 1 chance out of (number) that an EntityLightningBolt spawns when it's raining, setting it to 0 or lower will disable lightning bolts. (default: 10000)

- lightningFire: `/gamerule lightningFire <boolean>`  
Enables/disables fire created by ligthning. (default: true)

- lightningDamage: `/gamerule lightningDamage <float>`  
Damage dealt by ligthing (default: 5.0)

- lightningRange: `/gamerule lightningRange <double>`  
Entites inside this range will be damaged by ligthning (default: 3.0)

- lightningHorseSpawningModifier:  `/gamerule lightningHorseSpawningModifier <double>`  
Controls how often horse traps should spawn. (default: 0.01)

- doInsomnia:  `/gamerule doInsomnia <boolean>`  
Enables/disables phantom spawning. (default: true)

- tntExplodes:  `/gamerule tntExplodes <boolean>`  
Enables/disables tnt explosion. (default: true)

- explosionPowerModifier:  `/gamerule explosionPowerModifier <float>`  
Adjusts power of all explosions by multiplying it to the actual explosion power. (default: 1.0)

- pvp:  `/gamerule pvp <boolean>`  
Enables/disables pvp combat. (default: true on singleplayer, on servers, depends on the `pvp` property in server.properties)

- drowningDamage, fallDamage, fireDamage:  `/gamerule drowningDamage <boolean>`  
Enables/disables drowning, fall or fire damage, respectively. (default: true)

- doSnowMelt:  `/gamerule doSnowMelt <boolean>`  
Enables/disables snow and ice melting. (default: true)

- pistonPushLimit: `/gamerule pistonPushLimit <integer>`  
Controls the maximum number of blocks which can be moved by pistons. (default: 12)

- doHunger:  `/gamerule doHunger <boolean>`  
Enables/disables food bar depletion in survival. (default: true)

- instantRespawn:  `/gamerule instantRespawn <boolean>`  
If set to true, players will be immediately teleported to their respawn location without being shown the death screen. (default: false)

You can reset a numerical gamerule to its default value by setting to a negative value.

This mod requires Fabric (https://minecraft.curseforge.com/projects/fabric).
