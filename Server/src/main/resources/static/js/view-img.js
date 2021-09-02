async function injectLinks(){
    const url = window.location.href;
    const urlArr = url.split("/");
    const isViewImgUrl = (element) => element==="view-image";
    const indx = urlArr.findIndex(isViewImgUrl);
    const photoId = "/"+urlArr[indx+2];
    const albumId = "/"+urlArr[indx+1];
    document.getElementById('leftBtn').href = "/user/view-image/prev"+albumId+photoId;
    document.getElementById('rightBtn').href = "/user/view-image/next"+albumId+photoId;
    document.getElementById('albumBtn').href = "/user/view-album"+albumId;
    //document.getElementById('cartBtn').href =
    const response = await fetch('/user/get-image'+albumId+photoId, {
        method: 'get',
    });
    response.text().then(function  (text) {
        document.getElementById('currImg').src = text;
    });

}