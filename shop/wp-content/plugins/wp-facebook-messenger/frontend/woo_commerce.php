<?php
if ( !function_exists( 'add_action' ) ) {
    echo 'Hi there!  I\'m just a plugin, not much I can do when called directly.';
    exit;
}
function ninja_facebook_messenger_load_position(){
    add_action("woocommerce_before_add_to_cart_form","ninja_facebook_messenger_woo_botton");
}
add_action("init","ninja_facebook_messenger_load_position");
function ninja_facebook_messenger_woo_botton(){
    global $product;
    $product_id = $product->id;
    $enable = get_post_meta($product_id,"_facebook_messenger_enable",true);
    $custom = get_post_meta($product_id,"_facebook_messenger_custom",true);
    $custom_url = get_post_meta($product_id,"_facebook_messenger_url",true);
    $hide_cover = get_option("facebook_messenger_hide_cover");
    if( $hide_cover == 1 ) {
       $hide_cover = "true" ;
    }else{
        $hide_cover= "false";
    }
    if( $custom ==1 ) {
        $url = $custom_url;
    }else{
        $url = get_option("facebook_messenger_user");
    }
    //if( )
    echo do_shortcode('[messenger url="'.$url.'" hide_cover="'.$hide_cover.'"]');
}