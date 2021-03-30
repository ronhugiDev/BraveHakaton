import requests
from bs4 import BeautifulSoup


def get_site_links(site_link) -> list:
    """
    grabs all links from a site
    :param site_link: site link to grab from links
    :return: links list
    """
    links = []
    page = requests.get(site_link)
    soup = BeautifulSoup(page.content, 'html.parser')

    for link in soup.find_all('a'):
        link_content = link.get('href')
        links.append(link_content)
    return links


def get_story_by_link(link: str) -> str:
    """
    gets story by link
    :param link: story link
    :return: most "busiest" paragraph
    """
    page = requests.get(link)
    soup = BeautifulSoup(page.content, 'html.parser')
    content = soup.get_text()
    return get_story(content.strip())


def get_story(text: str) -> str:
    """
    :param   text: page content
    :return: most "busiest" paragraph
    """
    text = text.split('\n\n')       # split by paragraphs
    return max(text, key=len)

