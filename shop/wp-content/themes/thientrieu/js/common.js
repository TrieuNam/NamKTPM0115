jQuery(document).ready(function() {
   $('.togo').click(function() {
    $(this).toggleClass('avtive-mn');
    $(this).next().slideToggle(300);
   });
});

if($(window).width() < 990){
  $(document).mouseup(function (e){
  var container = $(".menu .block-menu");
  if (!container.is(e.target) && container.has(e.target).length === 0){
    container.hide();e.stopPropagation();
  }
});
}
$(document).ready(function(){
    $('a.smobitrigger').click(function(){
      $('.menu .block-menu').toggle();
    });

    $(".rslides").responsiveSlides({
      nav: true,
      prevText: "Previous",
      nextText: "Next"
    });

    $('.score-callback').raty({
      starOff    : Homeurl+'/images/front/star-off.png',
      starOn     : Homeurl+'/images/front/star-on.png',
      score: function() {
        return $(this).attr('data-score');
      }
    });


    $('.block-product .list-hot').slick({
        dots: false,
        infinite: false,
        arrows: true,
        autoplay: false,
        speed: 3000,
        slidesToShow: 4,
        slidesToScroll: 1,
        responsive: [{
          breakpoint: 990,
          settings: {
            slidesToShow: 3
          }
        },
        {
          breakpoint: 680,
          settings: {
            slidesToShow: 2
          }
        },
        {
          breakpoint: 480,
          settings: {
            slidesToShow: 1
          }
        }]
    });
    $('.block-product .list-news').slick({
        dots: false,
        infinite: false,
        arrows: true,
        autoplay: false,
        speed: 3000,
        slidesToShow: 4,
        slidesToScroll: 1,
        responsive: [{
          breakpoint: 990,
          settings: {
            slidesToShow: 3
          }
        },
        {
          breakpoint: 680,
          settings: {
            slidesToShow: 2
          }
        },
        {
          breakpoint: 480,
          settings: {
            slidesToShow: 1
          }
        }]
    });

});