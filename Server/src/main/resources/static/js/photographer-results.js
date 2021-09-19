const searchBtn = document.getElementById('search-btn');
const searchQuery = document.getElementById('searchQuery');
const errorMsgBox = document.getElementById('errorMsg');
const resultsDiv = document.getElementById('results');
const FIVE_STARS = 5;
searchBtn.addEventListener('click', getSearchResults)

async function getSearchResults(event) {
    var searchQueryVal = searchQuery.value;
    var validityErr;
    var errFlag = 0;
    //validation
    if (searchQueryVal == null) {
        errFlag = 1;
        return;
    }

    var namesArr = searchQueryVal.split(" ");
    if (namesArr.length < 2) {
        errFlag = 1;
        errorMsgBox.style.display = 'block';
        errorMsgBox.textContent = "Please type both First and Last name";
        return;
    }

    const response = await fetch("/user/search-photographer/" +searchQueryVal ,{
        method: "post",
    })

    if (response.ok){
        createResult(await response.json());
    }
    else
        resultsDiv.innerText = "No Photographer found"

}

function createResult(results){
    for (i=0;i<results.length;i++){
        const resultBoxDiv =document.createElement("div");
        const nameSpan = document.createElement("span");
        nameSpan.innerText = results[i].name;
        const emailA = document.createElement("a");
        emailA.href = "mailto: "+ results[i].email;
        const iconI = document.createElement("i");
        iconI.className = "far fa-envelope";
        emailA.appendChild(iconI);
        const br = document.createElement('br');
        const spanStars = document.createElement("span");
        createStarsIconsForRating(spanStars, results[i].rating);
        resultBoxDiv.appendChild(nameSpan);
        nameSpan.append(emailA, br, br, spanStars);
        resultsDiv.appendChild(resultBoxDiv);
    }


}