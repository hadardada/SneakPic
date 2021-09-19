const nameSpan = document.getElementById('name');
const rating = document.getElementById('rating');
const cameraIcon = document.getElementById('userType');
const isPhotographer = document.getElementById('isPhotographer');

async function injectDetails(){

    const response = await fetch('/user/is-photographer', {
        method: 'get',
    });
    if (response.ok){
        var myDetails = await response.json();
        await putUserDetails(myDetails);

    }
}

async function putUserDetails(myDetails){
    nameSpan.innerText = myDetails.name;
    if (myDetails.isPhotographer) //if user is photographer - show rating and more options on menu
    {
        cameraIcon.style.color = "#143a3e";
        rating.style.display="block";
        createStarsIconsForRating(rating, myDetails.rating);

        isPhotographer.style.display = "block";
    }
}