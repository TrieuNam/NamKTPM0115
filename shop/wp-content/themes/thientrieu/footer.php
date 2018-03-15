		<div id="footer">
			<div class="container">
				<div class="row">
					<div class="col-md-3 col-sm-3 col-xs-6">
					<?php $getposts = new WP_query(); $getposts->query('post_status=publish&p=2&post_type=page'); ?>
					<?php global $wp_query; $wp_query->in_the_loop = true; ?>
					<?php while ($getposts->have_posts()) : $getposts->the_post(); ?>
						<h3><?php the_title(); ?></h3>
						<p><?php echo teaser(50); ?><a style="color:#fff" href="<?php the_permalink(); ?>">Xem thêm</a></p>
					<?php endwhile; wp_reset_postdata(); ?>
					</div>
					<div class="col-md-3 col-sm-3 col-xs-6">
						<h3>Thời trang</h3>
						<ul>
						<!-- Get category -->
						<?php $args = array( 
						    'hide_empty' => 0,
						    'taxonomy' => 'product_cat',
						    'orderby' => id,
						    'parent' => 0
						    ); 
						    $cates = get_categories( $args ); 
						    foreach ( $cates as $cate ) {  ?>
								<li>
									<a href="<?php echo get_term_link($cate->slug, 'product_cat'); ?>"><?php echo $cate->name ?></a>
								</li>
						<?php } ?>
						</ul>
					</div>
					<div class="col-md-3 col-sm-3 col-xs-6">
						<h3>Tài khoản</h3>
						<ul>
							<li><a href="<?php bloginfo('url'); ?>/tai-khoan">Tài khoản của bạn</a></li>
							<li><a href="<?php bloginfo('url'); ?>/gio-hang">Giỏ hàng</a></li>
							<li><a href="<?php bloginfo('url'); ?>/tin-tuc">Tin tức</a></li>
							<li><a href="<?php bloginfo('url'); ?>/">Liên hệ</a></li>
						</ul>
					</div>
					<div class="col-md-3 col-sm-3 col-xs-6">
					<?php global $hk_options; ?>
						<h3>Liên hệ với chúng tôi</h3>
						<p>
							<span>Địa chỉ: <?php echo $hk_options[addft]; ?></span>
							<span>Email: <?php echo $hk_options[emailft]; ?></span>
							<span>Điện thoại: <?php echo $hk_options[phoneft]; ?></span>
							<span>Website: <?php echo $hk_options[web]; ?></span>
						</p>
					</div>
				</div>
			</div>
			<div class="copyright"> <?php echo $hk_options[copyr]; ?> - Xây dựng và phát triển bởi <a href="https://www.facebook.com/nam.trieu.90834">HEHE</a> </div>
		</div> <!-- end footer -->
	</div> <!-- end wrapper -->
	<script type="text/javascript" src="<?php bloginfo('template_directory' ); ?>/js/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="<?php bloginfo('template_directory' ); ?>/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<?php bloginfo('template_directory' ); ?>/js/responsiveslides.min.js"></script>
	<script type="text/javascript" src="<?php bloginfo('template_directory' ); ?>/js/simpleMobileMenu.js"></script>
	<script type="text/javascript" src="<?php bloginfo('template_directory' ); ?>/js/slick.min.js"></script>
	<script> var Homeurl = '<?php echo bloginfo("template_directory"); ?>'; </script>
	<script type="text/javascript" src="<?php bloginfo('template_directory' ); ?>/js/jquery.raty.js"></script>
	<script type="text/javascript" src="<?php bloginfo('template_directory' ); ?>/js/common.js"></script>
	<div id="fb-root"></div>
	<script>
	(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/vi_VN/sdk.js#xfbml=1&version=v2.7&appId=750688268378229";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
	</script>
	<?php wp_footer(); ?>

<!-- $CHAT -->
<script language="javascript">
    var f_chat_vs = "Version 2.1";
    var f_chat_domain =  "http://localhost:8080/shop/";    
    var f_chat_name = "Hỗ trợ độc giả";
    var f_chat_star_1 = "Chào bạn!";
    var f_chat_star_2 = 'Bạn có thể gửi câu hỏi về <a target="_blank" href="https://web.facebook.com/groups/https://www.facebook.com/nam.trieu.90834/">Hỏi đáp IT</a> hoặc trao đổi với tôi tại đây.<br/><em>Gửi vài giây trước</em>';
    var f_chat_star_3 = "<a href='javascript:;' onclick='f_bt_start_chat()' id='f_bt_start_chat'>Bắt đầu Chat</a>";
    var f_chat_star_4 = "Chú ý: Bạn phải đăng nhập <a href='http://facebook.com/' target='_blank' rel='nofollow'>Facebook</a> mới có thể trò chuyện.";
    var f_chat_fanpage = "https://www.facebook.com/nam.trieu.90834"; /* Đây là địa chỉ Fanpage*/
    var f_chat_background_title = "#F77213"; /* Lấy mã màu tại đây http://megapixelated.com/tags/ref_colorpicker.asp */
    var f_chat_color_title = "#fff";
    var f_chat_cr_vs = 21; /* Version ID */
    var f_chat_vitri_manhinh = "right:10px;"; /* Right: 10px; hoặc left: 10px; hoặc căn giữa left:45% */    
</script>
<!-- $Chat iCon Font (có thể bỏ) -->
<link rel="stylesheet" href="https://localhost:8080/shop//php/livechat/font-awesome.min.css" type="text/css"/>
<!-- $Chat Javascript (không được xóa) -->
<script src="https://localhost:8080/shop//php/livechat/chat.js?vs=2.1"></script>
<!-- $Chat HTML (không được xóa) -->
<div id='fb-root'></div>
<a title='Mở hộp Chat' id='chat_f_b_smal' onclick='chat_f_show()' class='chat_f_vt'><i class='fa fa-comments title-f-chat-icon'></i> Chat</a><div id='b-c-facebook' class='chat_f_vt'><div id='chat-f-b' onclick='b_f_chat()' class='chat-f-b'><i class='fa fa-comments title-f-chat-icon'></i><label id="f_chat_name"></label><span id='fb_alert_num'>1</span><div id='t_f_chat'><a href='javascript:;' onclick='chat_f_close()' id='chat_f_close' class='chat-left-5'>x</a></div></div><div id='f-chat-conent' class='f-chat-conent'><script>document.write("<div class='fb-page' data-tabs='messages' data-href='https://www.facebook.com/"+f_chat_fanpage+"' data-width='250' data-height='310' data-small-header='true' data-adapt-container-width='true' data-hide-cover='true' data-show-facepile='false' data-show-posts='true'></div>");</script><div id='fb_chat_start'><div id='f_enter_1' class='msg_b fb_hide'></div><div id='f_enter_2' class='msg_b fb_hide'></div><br/><p id='f_enter_3' class='fb_hide' align='center'><a href='javascript:;' onclick='f_bt_start_chat()' id='f_bt_start_chat'>Bắt đầu Chat</a></p><br/><p id='f_enter_4' class='fb_hide' align='center'></p></div><div id="f_chat_source" class='chat-single'></div></div></div>
<!-- #CHAT -->
</body>
</html>