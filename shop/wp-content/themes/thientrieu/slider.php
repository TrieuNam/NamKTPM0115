<ul class="rslides">
	<?php $getposts = new WP_query(); $getposts->query('post_status=publish&showposts=-1&post_type=slider'); ?>
	<?php global $wp_query; $wp_query->in_the_loop = true; ?>
	<?php while ($getposts->have_posts()) : $getposts->the_post(); ?>
		<li>
			<img src="<?php echo wp_get_attachment_url( get_post_thumbnail_id($post->ID) );?>" alt="<?php the_title(); ?>" />
			<div class="txt">
				<p><?php the_title(); ?></p>
			</div>
		</li>
	<?php endwhile; wp_reset_postdata(); ?>
</ul>