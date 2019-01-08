ExtraGamerules [![](http://cf.way2muchnoise.eu/full_310323_downloads.svg)](https://minecraft.curseforge.com/projects/extragamerules)
[![](http://cf.way2muchnoise.eu/versions/For%20MC_310323_all.svg)](https://minecraft.curseforge.com/projects/extragamerules)
=========================

This mod adds some gamerules that allows lightning to be fully configurable.

#### Gamerules:

- lightningProbablity: <code>/gamerule lightningProbability &lt;integer&gt;</code>  
This number means there is 1 chance out of (number) that an EntityLightningBolt spawns when it's raining, setting it to 0 or lower will disable lightning bolts. (default: 10000)

- lightningFire: <code>/gamerule lightningFire &lt;boolean&gt;</code>  
Enables/disables fire created by ligthning. (default: true)

- lightningDamage: <code>/gamerule lightningDamage &lt;float&gt;</code>  
Damage dealt by ligthing (default: 5.0)

- lightningRange: <code>/gamerule lightningRange &lt;double&gt;</code>  
Entites inside this range will be damaged by ligthning (default: 3.0)

- lightningHorseSpawningModifier:  <code>/gamerule lightningHorseSpawningModifier &lt;double&gt;</code>  
Controls how often horse traps should spawn. (default: 0.01)

- doInsomnia:  <code>/gamerule doInsomnia &lt;boolean&gt;</code>  
Enables/disables phantom spawning. (default: true)

- tntExplodes:  <code>/gamerule tntExplodes &lt;boolean&gt;</code>  
Enables/disables tnt explosion. (default: true)

- explosionPowerModifier:  <code>/gamerule explosionPowerModifier &lt;float&gt;</code>  
Adjusts power of all explosions by multiplying it to the actual explosion power. (default: 1.0)

You can reset a numerical gamerule to its default value by setting to a negative value.

This mod requires Fabric (https://minecraft.curseforge.com/projects/fabric).
