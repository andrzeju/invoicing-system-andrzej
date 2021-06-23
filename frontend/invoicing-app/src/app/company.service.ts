import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../environments/environment';
import { Company } from './company';

const ENDPOINT_URL = 'companies';

@Injectable({
    providedIn: 'root'
})
export class CompanyService {

    private options = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        withCredentials: true
    };

    constructor(private http: HttpClient) {
    }

    getCompanies(): Observable<Company[]> {
        return this.http.get<Company[]>(this.apiUrl(ENDPOINT_URL));
    }

    addCompany(company: Company): Observable<any> {
        return this.http.post<any>(this.apiUrl(ENDPOINT_URL), this.toCompanyRequest(company), this.options);
    }

    deleteCompany(id: number): Observable<any> {
        return this.http.delete<any>(this.apiUrl(ENDPOINT_URL, id), this.options);
    }

    editCompany(company: Company): Observable<any> {
        return this.http.put<Company>(this.apiUrl(ENDPOINT_URL, company.id), this.toCompanyRequest(company), this.options);
    }

    private apiUrl(service: string, id: number | null = null): string {
        const idInUrl = (id !== null ? '/' + id : '');

        return environment.apiUrl + '/' + service + idInUrl;
    }

    private toCompanyRequest(company: Company) {
        return {
            taxIdentificationNumber: company.taxIdentificationNumber,
            name: company.name,
            address: company.address,
            pensionInsurance: company.pensionInsurance,
            healthInsurance: company.healthInsurance,
        };
    }

}
