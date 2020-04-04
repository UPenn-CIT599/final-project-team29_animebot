# MCIT 591 Final Project
---
Team 29 - Slack Anime Bot

## Quick-Start Guide
---
1. Clone this project: `git clone https://github.com/UPenn-CIT599/final-project-team29_animebot.git anime-bot`
2. Open the anime-bot project in Eclipse
3. Right-click the jbot project and select Run As > Maven Install

![jbot Maven Install](images/readme-jbot-install.png)

4. Right-click on the animebot project module and select Run As > Maven Build...

![animebot Maven Build](images/readme-animebot-build.png)

5. Under Goals, enter 'spring-boot:run' and click Run

![animebot Maven Run Configuration](images/readme-animebot-build-goals.png)

6. Log into the Slack Workspace: https://591finalproject.slack.com
7. Navigate to the bot channel
8. You're now ready to talk with our animebot!

## How to Interact
---
### Available Commands
Below is a list of commands you can use to interact with our animebot:
* @animebot
* get manga
* get top anime
* get top manga

## Technical Specifications
---
### Overview
Our Slack Anime Bot is based on the [JBot Framework](https://github.com/rampatra/jbot)
All JBot Framework code is contained in the jbot project folder

We leverage the [Jikan API](https://jikan.moe/) to fetch information from MyAnimeList.net

## Known Issues
---
### Installation
* You may need to remove and re-add the JUnit Library to the animebot project Build Path for the code to compile