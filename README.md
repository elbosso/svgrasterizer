# svgrasterizer

<!---
[![start with why](https://img.shields.io/badge/start%20with-why%3F-brightgreen.svg?style=flat)](http://www.ted.com/talks/simon_sinek_how_great_leaders_inspire_action)
--->
[![GitHub release](https://img.shields.io/github/release/elbosso/svgrasterizer/all.svg?maxAge=1)](https://GitHub.com/elbosso/svgrasterizer/releases/)
[![GitHub tag](https://img.shields.io/github/tag/elbosso/svgrasterizer.svg)](https://GitHub.com/elbosso/svgrasterizer/tags/)
[![GitHub license](https://img.shields.io/github/license/elbosso/svgrasterizer.svg)](https://github.com/elbosso/svgrasterizer/blob/master/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/elbosso/svgrasterizer.svg)](https://GitHub.com/elbosso/svgrasterizer/issues/)
[![GitHub issues-closed](https://img.shields.io/github/issues-closed/elbosso/svgrasterizer.svg)](https://GitHub.com/elbosso/svgrasterizer/issues?q=is%3Aissue+is%3Aclosed)
[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/elbosso/svgrasterizer/issues)
[![GitHub contributors](https://img.shields.io/github/contributors/elbosso/svgrasterizer.svg)](https://GitHub.com/elbosso/svgrasterizer/graphs/contributors/)
[![Github All Releases](https://img.shields.io/github/downloads/elbosso/svgrasterizer/total.svg)](https://github.com/elbosso/svgrasterizer)
[![Website elbosso.github.io](https://img.shields.io/website-up-down-green-red/https/elbosso.github.io.svg)](https://elbosso.github.io/)

This project is helping with designing new graphics and icon imagery for
applications. Its only goal is to render vector graphics (SVG) into bitmap
graphics (PNG).

It has some quirks to get optimal results: Prior to the actual rendering,
all stroke widths are converted to the target resolution. If stroke widths
are found to be smaller than one pixel in width, they are enlarged so
that they are at least one pixel wide at the target resolution.

If the file names adhere to a certain pattern, the number found in them is
interpreted as the optimal target resolution. Do if there are two vectors
found with otherwise identical names where one seems to point to 36 pixels
squared as target resolution and the other one seems to point to 48
pixels as target resolution, the application will use the 36 pixels version to render
target resolution 24 pixels and it will use the 48 pixel version to
render 64 pixels target resolution (for example).

Furthermore, the app knows about layers in the following way: If the overall
theme of the icon set being designed uses a philosophy where an abstract 
drawing illustrates some entity (a document for example) and there 
are smaller emblems representing an action on said entity (edit, delete, create,...)
and the app finds layers in the svg appropriately named, it generates 
from the one vector file all versions with the emblems in the layers.

## Build

```
mvn compile exec:java
```

or 

```
mvn -U package assembly:single
```


