const statsSpan = document.getElementById('rate');
const rateStar1 = document.getElementById('star1');
const rateStar2 = document.getElementById('star2');
const rateStar3 = document.getElementById('star3');
const rateStar4 = document.getElementById('star4');
const rateStar5 = document.getElementById('star5');


rateStar1.addEventListener("mouseenter", hovered);
rateStar1.addEventListener("click", rate);

rateStar2.addEventListener("mouseenter", hovered);
rateStar2.addEventListener("click", rate);

rateStar3.addEventListener("mouseenter", hovered);
rateStar3.addEventListener("click", rate);

rateStar4.addEventListener("mouseenter", hovered);
rateStar4.addEventListener("click", rate);

rateStar5.addEventListener("mouseenter", hovered);
rateStar5.addEventListener("click", rate);


 function hovered(event){
    if(event.target == rateStar1){
        rateStar1.style.color='gold';
        rateStar2.style.color='grey';
        rateStar3.style.color='grey';
        rateStar4.style.color='grey';
        rateStar5.style.color='grey';
    }
    if (event.target == rateStar2){
        rateStar1.style.color='gold';
        rateStar2.style.color='gold';
        rateStar3.style.color='grey';
        rateStar4.style.color='grey';
        rateStar5.style.color='grey';
    }
    if(event.target == rateStar3){
        rateStar1.style.color='gold';
        rateStar2.style.color='gold';
        rateStar3.style.color='gold';
        rateStar4.style.color='grey';
        rateStar5.style.color='grey';
    }
    if (event.target == rateStar4){
        rateStar1.style.color='gold';
        rateStar2.style.color='gold';
        rateStar3.style.color='gold';
        rateStar4.style.color='gold';
        rateStar5.style.color='grey';
    }
    if(event.target == rateStar5){
        rateStar1.style.color='gold';
        rateStar2.style.color='gold';
        rateStar3.style.color='gold';
        rateStar4.style.color='gold';
        rateStar5.style.color='gold';
    }
}

 function rate (event){
    var rateNum;
if (event.target== rateStar1)
    rateNum = 1;
else if(event.target == rateStar2)
    rateNum =2;
else if(event.target == rateStar2)
    rateNum =3;
else if(event.target == rateStar2)
    rateNum =4;
else
    rateNum = 5;

    const url = window.location.href;
    const urlArr = url.split("/");
    const isViewAlbumUrl = (element) => element==="view-album";
    const indx = urlArr.findIndex(isViewAlbumUrl);
    const albumId = "/"+urlArr[indx+1];
    const response =  fetch("/user/rate"+albumId+"/"+rateNum, {
        method: "post",
    })
        rateStar1.style.display = 'none';
        rateStar2.style.display = 'none';
        rateStar3.style.display = 'none';
        rateStar4.style.display = 'none';
        rateStar5.style.display = 'none';
        statsSpan.innerText = "Thank You";



}
