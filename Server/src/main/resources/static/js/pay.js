const payButton = document.getElementById('finalPay');
const serverMsg = document.getElementById('msg');
const downloadPic = document.getElementById('download');
const paymentDetailsBox = document.getElementById('cardDetails');
const priceBox = document.getElementById('pay');

payButton.addEventListener('click', async event =>{
    const url = window.location.href;
    const urlArr = url.split("/");
    const isPayment = (element) => element==="payment";
    const indx = urlArr.findIndex(isPayment);
    const photoId = "/"+urlArr[indx+2];
    const albumId = "/"+urlArr[indx+1];
    payButton.href = "/user/add-purchase"+albumId+photoId;
    const response = await fetch ("/user/add-purchase"+albumId+photoId, {
        method:"post",
    })
    paymentDetailsBox.style.display="none";
    priceBox.style.display="none";

    if (response.ok){
        serverMsg.innerText = "Photo purchase is successfully done!";
        response.text().then(function (text){
            downloadPic.href = text;
        });
        serverMsg.style.display = "block";
        downloadPic.style.display = "block";
    }

    else if (response.status == 406) {// photographer is buying his own photos
        serverMsg.innerText = "A photographer cannot purchase his own photos";
        serverMsg.style.color = "red";
        serverMsg.style.display = "block";
    }

    else if (response.status == 208){ // this photo was already purchased by this user
        serverMsg.innerText = "You've already purchased this photo! You won't be charged for this attempt";
        response.text().then(function (text){
            downloadPic.href = text;
        });
        serverMsg.style.display = "block";
        downloadPic.style.display = "block";
    }

});

