const loadCompanies = async() => {
            const response = await fetch('http://localhost:7766/companies');
            const companies = await response.json();

            let companiesTable = document.getElementById("companyTable")

            companies.forEach(company => {
                let row = companiesTable.insertRow(-1);

                let tinCell = row.insertCell(0);
                tinCell.innerText = company.taxIdentificationNumber;

                let addressCell = row.insertCell(1);
                addressCell.innerText = company.address;

                let nameCell = row.insertCell(2);
                nameCell.innerText = company.name;

                let pensionInsuranceCell = row.insertCell(3);
                pensionInsuranceCell.innerText = company.pensionInsurance;

                let healthInsuranceCell = row.insertCell(4);
                healthInsuranceCell.innerText = company.healthInsurance;
            })
}

loadCompanies();