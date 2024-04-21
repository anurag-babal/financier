export function formatDateToIndian(date) {
    if (!date) return ''; // Handle empty date case

    const newDate = new Date(date);
    const day = String(newDate.getDate()).padStart(2, '0');
    const month = String(newDate.getMonth() + 1).padStart(2, '0'); // Months are zero-indexed
    const year = newDate.getFullYear();

    return `${day}-${month}-${year}`;
}