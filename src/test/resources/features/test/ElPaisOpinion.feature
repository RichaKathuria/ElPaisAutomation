Feature: To automate the given use cases.

  Scenario: To scrape articles and translate
    Given the user navigates to the Site
    And the user accepts the cookie alert on screen
    Then the user ensures that the website's text is displayed in Spanish
    And the user navigates to the Opinion section
    Then the user should be navigated to Opinion section
    And fetch the first five articles of the section
    And the user prints the title and content of first 5 articles and saves the cover image to local
    And user gets title of each article translated to English
    And the user identifies any words that are repeated more than twice across all headers combined and prints them