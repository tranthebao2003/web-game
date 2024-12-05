

const buttons = document.querySelectorAll('.filter-button');

document.addEventListener('DOMContentLoaded', () => {
    const filterButtons = document.querySelectorAll('.filter-button');
    const reviews = document.querySelectorAll('.review');

    console.log ("filterbuttons", filterButtons);
    console.log("reviews",reviews)
    filterButtons.forEach(button => {
        button.addEventListener('click', () => {
            const rating = button.getAttribute('data-rating');

            console.log("Giá trị data-rating được chọn là:", rating); // In giá trị ra console


            // Xóa lớp active khỏi tất cả các nút
            filterButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');

            // Lọc reviews
            reviews.forEach(review => {
                console.log(review.getAttribute('data-rating'))
                if (rating === 'all' || review.getAttribute('data-rating') === rating) {
                    review.style.display = 'block'; // Hiển thị
                } else {
                    review.style.display = 'none'; // Ẩn
                }
            });
        });
    });
});