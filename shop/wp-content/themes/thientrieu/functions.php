<?php
require get_template_directory() . '/core/init.php';
/********************************************************************
//remove tag width & height in post
********************************************************************/
add_filter( 'post_thumbnail_html', 'remove_width_attribute', 10 );
add_filter( 'image_send_to_editor', 'remove_width_attribute', 10 );
function remove_width_attribute( $html ) {
   $html = preg_replace( '/(width|height)="\d*"\s/', "", $html );
   return $html;
}
/********************************************************************
//Ản link post type ko cần thiết
********************************************************************/
add_action('admin_head', 'wpds_custom_admin_post_css');
function wpds_custom_admin_post_css() {
    global $post_type;
    if ($post_type == 'slider') {
        echo "<style>#edit-slug-box {display:none;}</style>";
    }
}
/********************************************************************
//config excerpt length
********************************************************************/
function custom_excerpt_length( $length ) {
	return 300;
}
add_filter( 'excerpt_length', 'custom_excerpt_length', 999 );
/********************************************************************
//Allow HTML tags in Widget title
********************************************************************/
function html_widget_title( $var) {
	$var = (str_replace( '[', '<', $var ));
	$var = (str_replace( ']', '>', $var ));
	$var = (str_replace( '__', '"', $var ));
	return $var ;
}
add_filter( 'widget_title', 'html_widget_title' );
/********************************************************************
//register short code url
********************************************************************/
add_filter('widget_text', 'do_shortcode');
// [url_base]
function url_base_function() {
	return get_bloginfo( "url" );
}
add_shortcode('url_base', 'url_base_function');


/********************************************************************
//register my menu in theme
********************************************************************/
add_action( 'init', 'register_my_menus' );
function register_my_menus(){
	register_nav_menus(
	array(
		'main_nav' => 'Main Nav',
		'cat_nav' => 'Danh mục',
		)
	);
}
/********************************************************************
//REGISTER SIDEBAR
********************************************************************/
if (function_exists('register_sidebar')){
	register_sidebar(array(
	'name'=> 'Cột bên',
	'id' => 'sidebar',
	));
	register_sidebar(array(
	'name'=> 'Sản phẩm trang chủ',
	'id' => 'sanpham',
	));
}

/********************************************************************
global teaser function
********************************************************************/
function teaser($limit) {
	$excerpt = explode(' ', get_the_excerpt(), $limit);
	if (count($excerpt)>=$limit) {
		array_pop($excerpt);
		$excerpt = implode(" ",$excerpt).'[...]';
	} else {
		$excerpt = implode(" ",$excerpt);
	}
	$excerpt = preg_replace('`\[[^\]]*\]`','',$excerpt);
	return $excerpt.'...';
}
/********************************************************************
Post Views
********************************************************************/
function setpostview($postID){
    $count_key ='views';
    $count = get_post_meta($postID, $count_key, true);
    if($count == ''){
        $count = 0;
        delete_post_meta($postID, $count_key);
        add_post_meta($postID, $count_key, '0');
    } else {
        $count++;
        update_post_meta($postID, $count_key, $count);
    }
}
function getpostviews($postID){
    $count_key ='views';
    $count = get_post_meta($postID, $count_key, true);
    if($count == ''){
        delete_post_meta($postID, $count_key);
        add_post_meta($postID, $count_key, '0');
        return "0";
    }
    return $count;
}
/********************************************************************
get link img post
********************************************************************/
function get_link_img_post(){
	global $post;
	preg_match_all('/src="(.*)"/Us',get_the_content(),$matches);
	$link_img_post = $matches[1];
	return $link_img_post;
}

/********************************************************************
check link thumbnail
********************************************************************/
function check_link_thumb($post_id) {
	$img_customfield = get_post_meta($post_id, 'thumb', true);
	$img_attachment_image = wp_get_attachment_image_src( get_post_thumbnail_id( $post->ID ), 'large' );
	// get thumbnail
	if ($img_customfield) {
		$link_thumb = $img_customfield;
	} elseif ($img_attachment_image) {
		$link_thumb = $img_attachment_image[0];
	} else {
		$link_thumb = "";
	}
	return $link_thumb;
}
/********************************************************************
get link thumbnail
********************************************************************/
function get_link_thumb($post_id) {
	$img_customfield = get_post_meta($post_id, 'thumb', true);
	$img_attachment_image = wp_get_attachment_image_src( get_post_thumbnail_id( $post->ID ), 'large' );
	$img_uploads = get_children( array('post_parent' => $post_id, 'post_status' => 'inherit', 'post_type' => 'attachment', 'post_mime_type' => 'image', 'order' => 'ASC', 'orderby' => 'menu_order ID', 'numberposts' => 1) );
	
	$post_content = get_post_field('post_content', $post_id);
	$img_post = preg_match_all('/\< *[img][^\>]*src *= *[\"\']{0,1}([^\"\'\ >]*)/',$post_content,$matches);
	$img_default = get_bloginfo('template_directory').'/images/no-thumb.png';
	
	// get thumbnail
	if ($img_customfield) {
		$link_thumb = $img_customfield;
	} elseif ($img_attachment_image) {
		$link_thumb = $img_attachment_image[0];
	} elseif ($img_uploads == true) {
		foreach($img_uploads as $id => $attachment) {
			$img = wp_get_attachment_image_src($id, 'full');
			$link_thumb = $img[0];
		}
	} elseif (count($matches[1]) > 0) {
		$link_thumb = $matches[1][0];
	} else {
		$link_thumb = $img_default;
	}
	return $link_thumb;
}
/********************************************************************
creating thumbnails (no permalink to story, image only)
********************************************************************/
function show_thumb($w,$h,$zc,$cropfrom) {
	global $post;
	$img_customfield = get_post_meta($post->ID, 'thumb', true);
	$img_attachment_image = wp_get_attachment_image_src( get_post_thumbnail_id( $post->ID ), 'large' );
	$img_uploads = get_children( array('post_parent' => $post->ID, 'post_status' => 'inherit', 'post_type' => 'attachment', 'post_mime_type' => 'image', 'order' => 'ASC', 'orderby' => 'menu_order ID', 'numberposts' => 1) );
	$img_post = preg_match_all('/\< *[img][^\>]*src *= *[\"\']{0,1}([^\"\'\ >]*)/',get_the_content(),$matches);
	$img_default = get_bloginfo('template_directory').'/images/no-thumb.png';

	$thumbnail = 'thumbnail.php';
	
	// get thumbnail
	if ($img_customfield) {
		print '<img src="'.get_bloginfo('template_directory').'/'.$thumbnail.'?src='.urlencode($img_customfield).'&amp;w='.$w.'&amp;h='.$h.'&amp;zc='.$zc.'&amp;a='.$cropfrom.'" alt="'.get_the_title($post).'" title="'.get_the_title($post).'"/>';
	} elseif ($img_attachment_image) {
		print '<img src="'.get_bloginfo('template_directory').'/'.$thumbnail.'?src='.urlencode($img_attachment_image[0]).'&amp;w='.$w.'&amp;h='.$h.'&amp;zc='.$zc.'&amp;a='.$cropfrom.'" alt="'.get_the_title($post).'" title="'.get_the_title($post).'"/>';
	} elseif ($img_uploads == true) {
		foreach($img_uploads as $id => $attachment) {
			$img = wp_get_attachment_image_src($id, 'full');
			print '<img src="'.get_bloginfo('template_directory').'/'.$thumbnail.'?src='.urlencode($img[0]).'&amp;w='.$w.'&amp;h='.$h.'&amp;zc='.$zc.'&amp;a='.$cropfrom.'" alt="'.get_the_title($post).'" title="'.get_the_title($post).'" />';
		}
	} elseif (count($matches[1]) > 0) {
		print '<img src="'.get_bloginfo('template_directory').'/'.$thumbnail.'?src='.urlencode($matches[1][0]).'&amp;w='.$w.'&amp;h='.$h.'&amp;zc='.$zc.'&amp;a='.$cropfrom.'" alt="'.get_the_title($post).'" title="'.get_the_title($post).'" />';
	} else {
		print '<img src="'.get_bloginfo('template_directory').'/'.$thumbnail.'?src='.urlencode($img_default).'&amp;w='.$w.'&amp;h='.$h.'&amp;zc='.$zc.'&amp;a='.$cropfrom.'" alt="'.get_the_title($post).'" title="'.get_the_title($post).'" />';
	}
}
/********************************************************************
custom post_type slider
********************************************************************/
add_theme_support( 'post-thumbnails' );
function change_woocommerce_menu_title( $translated )
{
    $translated = str_replace( 'Contact', 'Liên hệ', $translated );
    $translated = str_replace( 'WooCommerce', 'Đơn hàng', $translated );
    $translated = str_replace( 'Tóm tắt', 'Nội dung mô tả', $translated );
    return $translated;
}
add_filter( 'gettext', 'change_woocommerce_menu_title' );

function hk_remove_ordering() {
	remove_action( 'woocommerce_after_shop_loop', 'woocommerce_pagination', 10 );
	remove_action( 'woocommerce_before_main_content', 'woocommerce_breadcrumb', 20 );
	remove_action( 'woocommerce_single_product_summary', 'woocommerce_template_single_sharing', 50 );
	remove_action( 'woocommerce_single_product_summary', 'woocommerce_template_single_rating', 10 );
}
add_action('init', 'hk_remove_ordering');
function woo_share_and_ontact_hk(){ ?>
	<div class="social-product">
		<div class="fb-like" data-href="<?php the_permalink(); ?>" data-layout="button_count" data-action="like" data-show-faces="true" data-share="true"></div>
		<script src="https://apis.google.com/js/platform.js" async defer></script>
  		<g:plusone size="medium"></g:plusone>
	</div>
<?php }
add_action('woocommerce_single_product_summary', 'woo_share_and_ontact_hk', 60);

function woo_star_hk(){ ?>
	<p class="ster">
		<span><i class="fa fa-star"></i></span>
		<span><i class="fa fa-star"></i></span>
		<span><i class="fa fa-star"></i></span>
		<span><i class="fa fa-star"></i></span>
		<span><i class="fa fa-star"></i></span>
	</p>
<?php }
add_action('woocommerce_single_product_summary', 'woo_star_hk', 7);
function wooninja_remove_items() {
    remove_submenu_page( 'woocommerce', 'wc-status');
    remove_submenu_page( 'woocommerce', 'wc-addons');
}
add_action( 'admin_menu', 'wooninja_remove_items', 99, 0 );