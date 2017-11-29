<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/signal" prefix="signal" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Symulator odbiornika systemu światłowodowego</title>
    <meta name="description" content="website description"/>
    <meta name="keywords" content="website keywords, website keywords"/>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script type="text/javascript" src="js/modernizr-1.5.min.js"></script>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  </head>
  <body>
    <div id="main">
      <header>
        <div id="strapline">
          <div id="welcome_slogan">
            <h3>
              Symulator odbiornika systemu światłowodowego
            </h3>
          </div>
        </div>
        <nav>
          <div id="menubar">
            <ul id="nav">
              <li class="current">
                <a href="/">Strona Główna</a>
              </li>
            </ul>
          </div>
        </nav>
      </header>
      <div id="slideshow_container">
        <div class="slideshow">
          <ul class="slideshow">
            <li class="show"><img width="940" height="250" src="images/home_1.png" alt="Światłowody otworzyły nową erę w telekomunikacji"/></li>
            <li><img width="940" height="250" src="images/home_2.jpg" alt="Współczesne cyfrowe przetwarzanie sygnałów pozwala osiągnąć zdumiewające efekty"/></li>
          </ul>
        </div>
      </div>
      <div id="site_content">
        <div id="content">
          <div id="figure">
            <signal:simulation simulationData="${simulationData}" binaryData="${binaryData}" numberOfBits="${binaryData.numberOfBits}"/>
          </div>
        </div>
      </div>
    </div>
    <footer>
      <div id="footer_content">
        website template by
        <a href="http://www.freehtml5templates.co.uk">Free HTML5 Templates</a><br />
        Authors: Dominik Januszewicz Mirosław Wojciechowski
      </div>
    </footer>
    <script type="text/javascript" src="js/image_slide.js"></script>
  </body>
</html>
