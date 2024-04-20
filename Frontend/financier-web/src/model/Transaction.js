export class Transaction {
    constructor(
        id = undefined,
        category = '',
        amount = 0,
        description = '',
        date = new Date().toISOString().split('T')[0]
    ) {
        this.id = undefined;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }
}