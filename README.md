# RaSpyCam - (Encrypted MySQL Comm. between IOTs)

 Just a simple Application which achieves AES Encrypted Comunication through Mysql, between two >= IOTs. +Camera-Use.
 This is not something "important" in my opinion, it is just a "Random" thought that somehow managed to be that kind of thing.
_(Although it Started as A "Raspberry Spy Camera" = RaSpyCam)_. 🤷 For real I have no idea what should i say or not, lol look by yourself [...] <br>

## ```📝 Summary```

_(Everything **```MarkedDown```** means that can be found **under**  the section **```Step-By-Step```**)_

### Steps Before Using :

* ✔️ Make Sure All those **```Step-By-Step \ Getting Started```** configurations are already done.
* ✔️ Copy via **```SSH```** the **Files** _"RaspyCam.jar"_ & _"Run.sh"_ **From** _".\RaspyCam\artifacts\"_ **Into** a **```New Folder```**.
* ✔️ Copy via **```SSH```** the **Folder** _".\RaspyCam\\**lib**"_ **Into** the **```New Folder```**.
* ✔️ Install & Set-Up ```app-debug.apk & Credentials ``` Under _".\MySQL_RaSpyCam5\app\build\outputs\apk\debug"_.

_(in case you want the Application to **Run on Startup** GoTo **```Run Program on Startup```** section)_.
### How to use it :

* ✔️ **```Move into a Directory```** **```New Folder```**.
* ✔️ Run command "**sh ```Run.sh &```**".
* ✔️ Wait until Camera led is On.
* ✔️ Open The Android App  **Connect & TakeAPicture**




### How to Debug & Compile It :
... <br>
...


## ```🌟 Features```

- ##### ``` Current```
- - ```+``` Simple [AES Encryption](https://en.wikipedia.org/wiki/Advanced_Encryption_Standard).
- - ```+``` Simple [Camera Manipulation](https://github.com/caprica/picam).
- - ```+``` Simple MySQL Communication.
- - ```+``` Simplicity in Code.
- - ```+``` Impractical Way of Sending Pictures, lol
 

- ##### ```Those I Want```
- - ```-``` [RTMP Server](https://en.wikipedia.org/wiki/Real-Time_Messaging_Protocol) ?
- - ```-``` Use of [Tor's control protocol](https://github.com/PanagiotisDrakatos/T0rlib4j).
- - ```-``` [GPU Acceleration](https://github.com/doe300/VC4C) With [YOLO](https://github.com/pjreddie/darknet/wiki/YOLO:-Real-Time-Object-Detection).
- - ```-``` Use of [Post-quantum cryptography](https://en.wikipedia.org/wiki/Post-quantum_cryptography). lol

## ```🦵 Step-By-Step```

#### ```🦶 Step-By-Step \ Getting Started```

1. * ```Install Raspbian & etc.```
2. * Connect Via ```SSH``` [...]
3. * ```Configurate An Internet Connection```
4. * ```Camera Configuration```
5. * Install ```Java```
6. * Set-Up ```MySQL``` Database

#### ```🦶 Install Raspbian & etc.```
Pretty much, how to set-up Raspberry Pi Zero [...]<br>
Some Video Links: [0](https://www.youtube.com/watch?v=HSSpQDs4lfU),[1](https://www.youtube.com/watch?v=xj3MPmJhAPU) <br>
...

#### ```🦶 Configurate An Internet Connection```
_(There are two ways to Configurate A WiFi Internet Connection)_<br>

**First Way:**<br>
 ✔️ Execute Command: ```sudo nano /etc/wpa_supplicant/wpa_supplicant.conf``` And Add:
```

ctrl_interface=DIR=/var/run/wpa_supplicant GROUP=netdev
update_config=1

network={
        ssid="Your_Network_Name_X"
        psk="Your_Password"
}

```
✔️ Press Ctrl + X <br>
✔️ Press Y<br>
✔️ Press Enter-Key <br>

_**If** ❌ "Error reading lock file /etc/wpa_supplicant/wpa_supplicant.conf: Not enough data read" **Then**_<br>
Execute Command: ``` sudo rm /etc/wpa_supplicant/.wpa_supplicant.conf.swp```

**Second Way:**<br>
...

#### ```🦶 Camera Configuration```
⚠️ Make sure camera cable is well connected.

**Using A Pi Camera:** <br>

 * Run The Command: ```sudo raspi-config```<br>
 * GoTo ```Interfacing Options > Camera```<br> 
 * Press **\<YES\>** - **\<OK\>** - **\<Finish\>** <br>
 * ✔️ And you are Done

**Using A USB Camera:** <br>
...

#### ```🦶 Java```
_Install Java 8 by Executing the command:_<br>
```sudo apt-get install --yes openjdk-8-jdk```

#### ```🦶 MySQL```
⚠️ Make Sure MySQL Server supports Remote Access<br>

**If** _you decide to use db4free_ **Then**
* ✔️ click [Here](https://db4free.net/signup.php) And Singup
* ✔️ Log-In By Clicking [Here](https://db4free.net/phpMyAdmin/)
* ✔️ Click On Your Database and go to "📜 SQL" Tab
* ✔️ Paste This Script:
```SQL
CREATE TABLE RaSpy0(
	Ra    LONGBLOB,
	RaSpy LONGBLOB
);
```
* ✔️ Click on Button Saying "Go"

[Helpful Image](https://imgur.com/pyiqgTv)<br><br> 
**Else** <br> 
[...] and just Start From the third Checkmark Where it says: <br> 
"✔️  Click On Your Database..."

#### ```🦶 SSH```
PuTTy , FileZilla <br>
* [Instead of IP Host you can use "raspberrypi.local"](https://www.youtube.com/watch?v=d5yNZs_pQx4)
* [Image Link](https://imgur.com/aqqbMzz)
...
#### ```🦶 New Folder```
To Create A New Folder with name "RaSpyCamApp" Execute : ```mkdir RaSpyCamApp```
#### ```🦶 Move into a Directory```
To Move-into-a-Directory/Change-Directory use Command ```cd``` and the name of folder like here: ```cd RaSpyCamApp```
#### ```🦶 Run.sh &```
|(Run.sh) Command Structure |
| ------ |
| ```java -cp "RaspyCam.jar:lib/*" app.App MySQL=[UserName, Password, ServerName, DatabaseName, Port, SSL_Boolean, DB_Column1, DB_Column2] , Camera=[Width, Height, Quality] , AES=[Key_Password]``` |

|Class & Atributes|Default Values | Informations |
| ------ | ------ | ------ |
|**```MySQL```**|**```MySQL```**|**```MySQL```**|
|UserName| Username | Server's Log-In Username |
|Password| Password | Server's Log-In Password |
|ServerName| db4free.net | Server's Link |
|DatabaseName| raspycam | Database's Name|
|Port| 3306 | Port number |
|SSL_Boolean| false | SSL Enabling If Supported|
|DB_Column1| RaSpy | Raspberry's Column|
|DB_Column2| Ra | Other Device's Column|
|**```Camera```**|**```Camera```**|**```Camera```**|
|Width|648| Frame's Width |
|Height|486|Frame's Height |
|Quality|77|Frame's Quality|
|**```AES```**|**```AES```**|**```AES```**|
|Key_Password|RaSpyCamKeyPswrd| 16-Byte Only Encryption Key |

#### ```🦶 app-debug.apk & Credentials ```
...
#### ```🦶 Run Program on Startup```
* [Helpful Link](https://learn.sparkfun.com/tutorials/how-to-run-a-raspberry-pi-program-on-startup/all) Or
* ✔️ Execute Command ```sudo nano /etc/rc.local```
* ✔️ Add Those Lines between "fi" and "exit 0" :
```
cd /home/pi/🦶 New Folder/
sh Run.sh &
```
* ✔️ Press Ctrl + X <br>
* ✔️ Press Y<br>
* ✔️ Press Enter-Key <br>

## ```🖼️ Output Images```
...

## ```❗ Important```
* ❗ The Fact that i am programming about ~10 years now DOESN'T MAKE ME A PROFESSIONAL PROGRAMMER
* ❗ [An Android App was the worst way i could communicate with a MySQL Database (At least in the way i did it?)](https://stackoverflow.com/questions/6373826/execute-asynctask-several-times)
* ❗ My brain isn't at it's best Right Now

## ```⚠️ Disclaimer```
* ⚠️ By IOTs in this case I specifically refer to Raspberry Pi Zero Devices and your PC.
* ⚠️ By far the worst way of communicating with a Pi from a prespective of Security.
* etc.




## ```🌐 Searches Across Internet```

| About |  |  |  |  |  |
| ------ | ------ | ------ | ------ | ------ | ------ |
|||||||
|||||||
|||||||
|||||||
|||||||
|||||||
|||||||
|||||||
|||||||
|||||||
|||||||

## ```📓 Notes```

* [An Android App was the worst way i could communicate with a MySQL Database](https://stackoverflow.com/questions/6373826/execute-asynctask-several-times), Trust me, if you want to make something practical you only have to create something that won't communicate with an Android phone but with another IOT device or even you main PC by just using the same code from "Raspycam" project to create an another Java Application that will communicate properly, because [...] or just dont use what i've made because it is pretty WTF in general [...] +This is by far the worst way of communicating with a Pi from a prespective of Security (you let everything open to anyone who can read your Pi)

* I have so many more important things to work on, that i have no idea how and why i made what i 've made and uploaded what i've uploaded, i don't know if actually there is any reason behind, but always it depends so ... yeah, have a nice day people out there. 

* It Works ...

* The answer To what you've asked was always the question  [...]

*  Εικόνα που βλέπει το "εγώ", θυμίζει κουτί που δε βλέπεις, εικόνα που χάνεις• σκιά που 'τε φως και να βλέπεις, ακούει τα λόγια που λες: [...][ ‮#i📁👁](https://www.instagram.com/explore/tags/i%F0%9F%93%81%F0%9F%91%81/)

[I wish love and happiness to Everyone! <3](https://www.youtube.com/watch?v=R914iTXtBfU)
## ```Donation```
+ [![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=JVUMG5YLVY7GA&source=url)
+ Bitcoin Address:

## ```HasTags```
#MόνοΠαράνοια#ΆιΜιςΓιου#2κ16#μάι#ντίαρ#σελφ
