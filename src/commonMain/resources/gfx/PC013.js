(function(name,data){
 if(typeof onTileMapLoaded === 'undefined') {
  if(typeof TileMaps === 'undefined') TileMaps = {};
  TileMaps[name] = data;
 } else {
  onTileMapLoaded(name,data);
 }
 if(typeof module === 'object' && module && module.exports) {
  module.exports = data;
 }})("PC013",
{ "compressionlevel":-1,
 "editorsettings":
    {
     "export":
        {
         "format":"json",
         "target":"PC013..json"
        }
    },
 "height":8,
 "infinite":false,
 "layers":[
        {
         "data":[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48],
         "height":8,
         "id":1,
         "name":"layer",
         "opacity":1,
         "type":"tilelayer",
         "visible":true,
         "width":6,
         "x":0,
         "y":0
        }],
 "nextlayerid":2,
 "nextobjectid":1,
 "orientation":"orthogonal",
 "renderorder":"right-down",
 "tiledversion":"1.3.3",
 "tileheight":90,
 "tilesets":[
        {
         "columns":8,
         "firstgid":1,
         "image":"tiles.png",
         "imageheight":540,
         "imagewidth":720,
         "margin":0,
         "name":"tiles",
         "spacing":0,
         "tilecount":48,
         "tileheight":90,
         "tilewidth":90
        }],
 "tilewidth":90,
 "type":"map",
 "version":1.2,
 "width":6
});