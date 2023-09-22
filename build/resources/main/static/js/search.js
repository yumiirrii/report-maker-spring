import { setCheckedItemsToValue } from './common.js';

/* アコーディオン */
const yearMonth = getCurrentYearMonth();
const accordionHeaders = document.querySelectorAll(".accordion-header");

accordionHeaders.forEach(header => {
    if (header.textContent.includes(yearMonth)) {
        header.nextElementSibling.style.display = "block";
    }
    header.addEventListener("click", function() {
        const content = this.nextElementSibling;
        content.style.display = content.style.display === "block" ? "none" : "block";
    });
})

function getCurrentYearMonth() {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0'); // Months are 0-based

    const yearMonth = `${year}-${month}`;
    return yearMonth;
}

/* チェックボックス */
const deleteReportIds = document.getElementById("deleteReportIds");
setCheckedItemsToValue(deleteReportIds);


