const categories = [
    {id: 1, name: 'Grocery'},
    {id: 2, name: 'Rent'},
    {id: 3, name: 'Transport'},
    {id: 4, name: 'Medical'},
    {id: 5, name: 'Entertainment'},
    {id: 6, name: 'Education'},
    {id: 7, name: 'Others'},
];

export async function getCategories() {
    return categories;
}