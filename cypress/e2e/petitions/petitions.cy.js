describe('Stephen\'s Petitions E2E', () => {
  it('creates a petition and shows it in the petitions list', () => {
    const uniqueTitle = `Cypress Petition ${Date.now()}`;
    const uniqueDescription = 'Created by Cypress as a browser automation check';

    cy.visit('/create');

    cy.get('[data-cy="create-page-title"]').should('contain', 'Create a Petition');
    cy.get('[data-cy="create-form"]').should('exist');
    cy.get('[data-cy="petition-title-input"]').type(uniqueTitle);
    cy.get('[data-cy="petition-description-input"]').type(uniqueDescription);
    cy.get('[data-cy="create-petition-button"]').click();

    cy.url().should('include', '/petitions');
    cy.get('[data-cy="petitions-page-title"]').should('contain', 'All Petitions');
    cy.get('[data-cy="petition-list"]').should('contain', uniqueTitle);
  });

  it('searches for a newly created petition and shows the result', () => {
    const uniqueTitle = `Searchable Petition ${Date.now()}`;
    const uniqueDescription = 'Created for Cypress search verification';

    cy.visit('/create');

    cy.get('[data-cy="petition-title-input"]').type(uniqueTitle);
    cy.get('[data-cy="petition-description-input"]').type(uniqueDescription);
    cy.get('[data-cy="create-petition-button"]').click();

    cy.url().should('include', '/petitions');
    cy.get('[data-cy="search-petitions-link"]').click();

    cy.url().should('include', '/search');
    cy.get('[data-cy="search-page-title"]').should('contain', 'Search Petitions');
    cy.get('[data-cy="search-form"]').should('exist');
    cy.get('[data-cy="search-keyword-input"]').type(uniqueTitle);
    cy.get('[data-cy="search-submit-button"]').click();

    cy.url().should('include', '/search/results');
    cy.get('[data-cy="results-page-title"]').should('contain', 'Search Results');
    cy.get('[data-cy="search-keyword-value"]').should('contain', uniqueTitle);
    cy.get('[data-cy="results-list"]').should('contain', uniqueTitle);
  });
});