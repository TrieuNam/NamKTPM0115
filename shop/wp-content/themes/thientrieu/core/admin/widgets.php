<?php
/********************************************************************
//widget bài viết theo chuyên mục trang chủ
********************************************************************/
class Main_Category_widget extends WP_Widget {
  function Main_Category_widget() {
    $widget_ops = array( 'classname' => 'main_category_widget', 'description' => 'Tùy chỉnh các chuyên mục ngoài trang chủ' ); // Widget Settings
    $control_ops = array( 'id_base' => 'main_category_widget' ); // Widget Control Settings
    $this->WP_Widget( 'Main_Category_widget', '1 Main Category', $widget_ops, $control_ops ); // Create the widget
  }
    function widget($args, $instance) {
      extract( $args );
      $title              = apply_filters('widget_title', $instance['title']); // the widget title
      $catid              = $instance['catid']; // the widget title
      $postsnumber_img    = $instance['postsnumber_img'];
?>
<?php 
if ( !defined('ABSPATH') )
  die('-1');
?>
    <div class="col-md-4 col-sm-4 col-xs-6">
      <h3><span><?php echo $title; ?></span></h3>
      <ul>
        <?php $ids = $catid; $term2 = get_term_by( 'id',$ids,'product_cat' ); $slug=$term2->slug;?>
        <?php $args = array('post_type'=>'product','posts_per_page'=>$postsnumber_img, 'product_cat'=>$slug ); ?>
        <?php $getposts = new WP_query( $args);?>
        <?php global $wp_query; $wp_query->in_the_loop = true; ?>
        <?php while ($getposts->have_posts()) : $getposts->the_post(); ?>
        <?php global $product; ?>
        <li>
          <div class="detail">
            <div class="img-fasti">
              <a href="<?php the_permalink(); ?>" class="img"><?php show_thumb(270,300,1,'c'); ?></a>
              <form action="" method="post">
                  <input type="hidden" name="add-to-cart" value="<?php the_id(); ?>">
                  <button class="add-icon"><i class="fa fa-cart-plus"></i></button>
              </form>
              <a class="v-icon" href="<?php the_permalink(); ?>"><i class="fa fa-search-plus"></i></a>
            </div> 
            <div>
              <h4><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a></h4>
              <div class="score-callback" data-score="4"></div>
              <p><span class="price-pro"><?php echo $product->get_price_html(); ?></span></p>
            </div>
          </div>
        </li>
        <?php endwhile; ?>
      </ul>
    </div>

    <?php } 
    function update($new_instance, $old_instance) {
      $instance['title']              = strip_tags($new_instance['title']);
      $instance['postsnumber_img']    = strip_tags($new_instance['postsnumber_img']);
      $instance['catid']              = strip_tags($new_instance['catid']);
      return $instance;
    }

  function form($instance) {
  $defaults = array( 'title' => 'Chuyên mục', 'catid' => '12' , 'postsnumber_img' => '2' );
  $instance = wp_parse_args( (array) $instance, $defaults ); ?>

    <p>
      <label for="<?php echo $this->get_field_id('title'); ?>">Title:</label>
     <input class="widefat" id="<?php echo $this->get_field_id('title'); ?>" name="<?php echo $this->get_field_name('title'); ?>'" type="text" value="<?php echo $instance['title']; ?>" />
    </p>
    <p>
      <label for="<?php echo $this->get_field_id('catid'); ?>"><?php _e('Chọn chuyên mục: '); ?></label>
      <?php
        wp_dropdown_categories( array(
          'orderby'      => 'title',
          'hide_empty'   => false,
          'hierarchical' => 1,
          'taxonomy'           => 'product_cat',
          'name'         => $this->get_field_name( 'catid' ),
          'id'           => 'rpjc_widget_cat_recent_posts_category',
          'class'        => 'widefat',
          'selected'     => $instance['catid']
        ) );
      ?>
    </p>
    <p>
      <label for="<?php echo $this->get_field_id('postsnumber_img'); ?>"><?php _e('Số lượng bài viết '); ?></label>
      <input class="widefat" id="<?php echo $this->get_field_id('postsnumber_img'); ?>" name="<?php echo $this->get_field_name('postsnumber_img'); ?>" type="number" value="<?php echo $instance['postsnumber_img']; ?>" min="0" />
    </p>
    <?php }
}
  add_action('widgets_init', create_function('', 'return register_widget("Main_Category_widget");'));
  ?>