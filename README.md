ExtraGamerules
=========================

This mod adds some gamerules that allows lightning to be fully configurable.

#### Gamerules:

- lightningProbablity: <code>/gamerule lightningProbability &lt;integer&gt;</code>  
This number means there is 1 chance out of (number) that an EntityLightningBolt spawns when it's raining, setting it to 0 or lower will disable lightning bolts. (default: 100000)

- lightningFire: <code>/gamerule lightningFire &lt;boolean&gt;</code>  
Enables/disables fire created by ligthning. (default: true)

- lightningDamage: <code>/gamerule lightningDamage &lt;float&gt;</code>  
Damage dealt by ligthing (default: 5.0)

- lightningRange: <code>/gamerule lightningRange &lt;double&gt;</code>  
Entites inside this range will be damaged by ligthning (default: 3.0)

- lightningHorseSpawning:  <code>/gamerule lightningRange &lt;boolean&gt;</code>  
Should lightning spawn skeleton horses? (default: true)

Setting any of this values to an invalid string such as "covefe" will set it to its default vaulue.

This mod requires Fabric (https://minecraft.curseforge.com/projects/fabric).