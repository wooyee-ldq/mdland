$(function(){

    var oldText = $("#editArea").val();
    $("#editArea").on('input propertychange', function() {
        var currentVal = $(this).val();
        if(currentVal != oldText){
            oldText = currentVal;
            render(currentVal)
        }
    
    });

    // $("#editArea").on("input propertychange", function(){
    //     alert(123);
    // });

    function render(text){
        var converter = new showdown.Converter();
        var html = converter.makeHtml(text);
        var htmlElem = $("#html");
        htmlElem.empty();
        htmlElem.append(html);
    }

















});

// function render(){
//     var converter = new showdown.Converter();
//     var text = $("#editArea").val();
//     var html = converter.makeHtml(text);
//     var htmlElem = $("#html");
//     htmlElem.empty();
//     htmlElem.append(html);
// }