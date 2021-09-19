const albumsBox = document.getElementById('AlbumsBox');
const viewAlbums = document.getElementById('title-albums');
const MY_UPLOADS = 1;
const MY_MATCHES = 2;
var currLat;
var currLng;


 async function injectDetails(){
    //check if it's a view my uploaded albums page (for photographers)
    //or view-matching albums (for all users)
    const url = window.location.href;
    const urlArr = url.split("/");
    const isViewUploadedAlbums = (element) => element === "view-my-albums";
    const indx = urlArr.findIndex(isViewUploadedAlbums);
    var response;
    if (indx === -1) {// meaning it's a view-matching albums page
        response = await fetch("/user/get-matching-albums",{
            method: "get",
        });
        if (response.ok){
            createResult(await response.json(), MY_MATCHES);
        }
        else{
            albumsBox.innerText = "No Albums Found. Keep checking-in and you might find a matching album";

        }
    }
    else{// meaning it's a view my upload albums page for photographers
        response = await fetch("/user/get-my-albums",{
            method: "get",
        });

        if (response.ok){
            createResult(await response.json(), MY_UPLOADS);
        }
        else {
            albumsBox.innerText = "No Albums Found";
        }

    }
}

function createResult(albumDetails, type){
    for (i=0;i<albumDetails.length;i++){
        const resultBoxDiv =document.createElement("div");
        resultBoxDiv.className="result";
        const resultBoxA =document.createElement("a");
        const nameSpan = document.createElement("div");
        nameSpan.innerText ="Title: "+albumDetails[i].name;
        const rangeFromSpan = document.createElement("div");
        rangeFromSpan.innerText ="From: "+albumDetails[i].fromTime;
        const rangeToSpan = document.createElement("div");
        rangeToSpan.innerText ="To: "+albumDetails[i].toTime;
        const uploadedTimeSpan = document.createElement("div");
        uploadedTimeSpan.innerText="Uploaded On: "+albumDetails[i].uploadTime;
        const uploadedBySpan = document.createElement("div");
        uploadedBySpan.innerText = "Uploaded By: "+albumDetails[i].photographer;
        const mapDiv = document.createElement("div");
        mapDiv.id = "map";
        resultBoxA.appendChild(nameSpan);
        resultBoxA.appendChild(rangeFromSpan);
        resultBoxA.appendChild(rangeToSpan);
        resultBoxA.appendChild(uploadedTimeSpan);
        if (type=== MY_UPLOADS)
            resultBoxA.appendChild(uploadedBySpan);
        resultBoxA.href = "/user/view-album/"+albumDetails[i].albumId;
        const mapImg = document.createElement("img");

        mapImg.src ="https://maps.googleapis.com/maps/api/staticmap?center="
        +albumDetails[i].lat+","+albumDetails[i].lng+"&zoom=15&size=250x100&markers=color:blue%7C"+
            +albumDetails[i].lat+","+albumDetails[i].lng+"&key=AIzaSyBgQppaGx-QFHdBvhmV4YD1wPEs_EygwJc";
        mapDiv.appendChild(mapImg);
        resultBoxA.appendChild(mapDiv);
        resultBoxDiv.appendChild(resultBoxA);
        albumsBox.appendChild(resultBoxDiv);
}}

