

window.onload = () => {
    request();
}


function getId() {
    // url 에서 id 잘라서 요청
    let url = location.href;
    let id = url.substring(url.lastIndexOf('/') + 1);

    return id;
}


function load() {
    request();
}


function request() {

    // jquery 
    $.ajax({
        async: false, 
        type: "get", 
        url: "/api/news/" + getId(),
        // * 보낼 데이터 없음
        dataType: "json", 
        success: (response) => {
            console.log(response);

            setNewsData(response.data);
        },
        error: (error) => {
            console.log(error);
        } 
    })    
}

function setNewsData(news) {
    const newsTitle = document.querySelector(".news-title");
    const newsBroadcasting = document.querySelector(".news-broadcasting");
    const newsWriter = document.querySelector(".news-writer");
    const newsCreateDate = document.querySelector(".news-create-date");
    const newsContent = document.querySelector(".news-content");
    const newsFile = document.querySelector(".news-file");

    // 값 넣어주기 news
    newsTitle.textContent = news.title;
    newsBroadcasting.textContent = news.broadcastingName;
    newsWriter.textContent = news.writer;
    newsCreateDate.textContent = news.createDate;
    newsContent.textContent = news.content;
    // filename1.jpg / filename2.jpg
    newsFile.textContent = `
        filename1.jpg / filename2.jpg
    `;

}