export class Company {

    constructor(
        public taxIdentificationNumber: string,
        public name : string,
        public address : string,
        public pensionInsurance: number,
        public healthInsurance: number
    ) {}
} 