(function( $ ) {
    // Add Color Picker to all inputs that have 'color-field' class
    $(function() {
        $('#facebook_messenger_backgroud').wpColorPicker();
    });
    var fileInput = '';
    jQuery('#fecebook-messenger-upload').click(function() {
        fileInput = "#facebook_messenger_text_img";
        tb_show('', 'media-upload.php?type=image&amp;TB_iframe=true');
        return false;
    });
    window.send_to_editor = function(html) {
     imgurl = jQuery(html).attr('src');
     $("#facebook_messenger_text_img").val(imgurl);
     tb_remove();
    }
    $("#facebook-messenger-checkall").change(function(){
        $(".facebook_messenger_hide_page").prop('checked', $(this).prop("checked"));
    })
    $("#facebook-messenger-checkall-1").change(function(){
        $(".facebook_messenger_show_page").prop('checked', $(this).prop("checked"));
    })
    $("#ninja-display-messenger").change(function(){
        var id = $(this).val();
        if ( id == 1 ) {
            $("#facebook-messenger-tr-show").removeClass("hidden");
            $("#facebook-messenger-tr-hide").addClass("hidden");
        }else{
            $("#facebook-messenger-tr-hide").removeClass("hidden");
            $("#facebook-messenger-tr-show").addClass("hidden");
        }
    })
    $("#fecebook-messenger-default-icon").click(function(e){
        $("#facebook_messenger_text_img").val(object_name.url);
        return false;
    })
})( jQuery );