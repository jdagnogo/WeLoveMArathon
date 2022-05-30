<h1 align="center">WeLoveMArathon</h1>
<div align="center">
  <strong>The Official WeLoveMarathon 's App</strong>
</div>

## Context
I am volunteering and we want to help local business to be more attractive. In order to do so, we decided to create an app, specialize in the city of Marathon in Greece. I did the Android and the verbose part. I started to learn, compose while coding this app. Also, I am working on this only after work or weeks. There is a lot I can improve!  
I came up with the the version 1, but the app was not sexy. So we met a designer who did the mockup for the new version. 
Please take that into consideration, while reading the code.

## :art: Pattern

### MVI 
- Use of reducer, state and partial state and events. The viewModel expose only one pubic method : fun dispatchEvent(event : SEALED_CLASS)

### Clean architecture
ViewModel communicates with UseCases. UseCase communicates with the repository. The repository uses the SingleTruthStrategy to either fetch from local (room) or from the server (firebase).

### SingleTruthStrategy
- This app will use the free version of Firebase. Also the content will not be updated very often. So I implemented a cache strategy that does the request only when the cache is not fresh. The rest we will just display the data from the database. In debug the time is really short. In production, we will decide later (maybe a day or a week). Please check SingleSourceOfTruthStrategy.kt for more info.


### Jetpack Compose:
All the component have a statefull and a stateless version ex: FoodScreen and FoodContent
 - The viewModel hold all the logic, the statefull component does not take any decison but simple listen the state of the ViewModel : the ViewModel expose the values as StateFlow and the stateFull component simply transform this flow into a Compose State and observe it. This way the app will be easier to tested as the logic will be unit tested. 
 - I tried to write some ui compose tests but as it looks a lot like espresso.. i kind of forget about it for the moment. 
 - I have recently updated the compose 1.2 with the key value to improve the perfomance of lists.

## Screenshots new version
<p align="center">
    <img src="https://github.com/jdagnogo/WeLoveMArathon/blob/main/screens/new-version/Screenshot_20220530_184044_com.jdagnogo.welovemarathon.debug.jpg" width="256" height="455">
  <img src="https://github.com/jdagnogo/WeLoveMArathon/blob/main/screens/new-version/Screenshot_20220530_184054_com.jdagnogo.welovemarathon.debug.jpg" width="256" height="455">
  <img src="https://github.com/jdagnogo/WeLoveMArathon/blob/main/screens/new-version/Screenshot_20220530_184100_com.jdagnogo.welovemarathon.debug.jpg" width="256" height="455">
  <img src="https://github.com/jdagnogo/WeLoveMArathon/blob/main/screens/new-version/Screenshot_20220530_184108_com.jdagnogo.welovemarathon.debug.jpg" width="256" height="455">
  <img src="https://github.com/jdagnogo/WeLoveMArathon/blob/main/screens/new-version/Screenshot_20220530_184118_com.jdagnogo.welovemarathon.debug.jpg" width="256" height="455">
    <img src="https://github.com/jdagnogo/WeLoveMArathon/blob/main/screens/new-version/Screenshot_20220530_184123_com.jdagnogo.welovemarathon.debug.jpg" width="256" height="455">
</p>

## Screenshots Old version
<p align="center">
  <img src="https://github.com/jdagnogo/WeLoveMArathon/blob/main/screens/screen_3.jpg" width="256" height="455">
  <img src="https://github.com/jdagnogo/WeLoveMArathon/blob/main/screens/screen_1.jpg" width="256" height="455">
  <img src="https://github.com/jdagnogo/WeLoveMArathon/blob/main/screens/screen_2.jpg" width="256" height="455">
  <img src="https://github.com/jdagnogo/WeLoveMArathon/blob/main/screens/screen_4.jpg" width="256" height="455">
  <img src="https://github.com/jdagnogo/WeLoveMArathon/blob/main/screens/screen_5.jpg" width="256" height="455">
</p>


## License

All the code available under the MIT license. See [LICENSE](LICENSE).

```
MIT License

Copyright (c) 2020 Zsolt Kocsi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
