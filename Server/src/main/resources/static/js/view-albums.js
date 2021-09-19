const albumsBox = document.getElementById('AlbumsBox');
const viewAlbums = document.getElementById('title-albums');
const MY_UPLOADS = 1;
const MY_MATCHES = 2;

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
        const resultBoxDiv =document.createElement("a");
        const nameSpan = document.createElement("span");
        nameSpan.innerText ="Title: "+albumDetails[i].name;
        const br = document.createElement('br');
        const rangeSpan = document.createElement("span");
        rangeSpan.innerText ="Time Range: "+albumDetails[i].fromTime+" - "+ albumDetails[i].toTime;
        const uploadedTimeSpan = document.createElement("span");
        uploadedTimeSpan.innerText="Uploaded On: "+albumDetails[i].uploadTime;
        const uploadedBySpan = document.createElement("span");
        uploadedBySpan.innerText = "Uploaded By: "+albumDetails[i].photographer;
        resultBoxDiv.appendChild(nameSpan);
        nameSpan.append(br,rangeSpan,br,uploadedTimeSpan,br);
        if (type=== MY_UPLOADS)
            nameSpan.append(uploadedBySpan, br);
        resultBoxDiv.href = "/user/view-album/"+albumDetails[i].albumId;
        albumsBox.appendChild(resultBoxDiv);
}}