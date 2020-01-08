function getNews()
{
    $.ajax({
        url: "http://localhost:9092/news",
        type: "GET",
        crossDomain: true,
        success: function (response) {
            //var resp = JSON.parse(response);
            $( "#result" ).html( JSON.stringify(response ));
            //alert(response);
        },
        error: function (xhr, status) {
            alert("error");
        }
    });
}